package com.kumbarakala.app.data.repository

import android.content.Context
import android.net.Uri
import com.kumbarakala.app.data.local.dao.ProductDao
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.util.ImageUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productDao: ProductDao
) {

    /**
     * Add a new product with photo.
     */
    suspend fun addProduct(
        artisanId: Int,
        name: String,
        category: String,
        description: String,
        price: String,
        photoUri: Uri?
    ): Result<Int> {
        return try {
            var photoPath = ""

            // Save photo if provided
            if (photoUri != null) {
                val tempProduct = Product(artisanId = artisanId)
                val id = productDao.insert(tempProduct).toInt()
                photoPath = saveProductPhoto(photoUri, artisanId, id)
                    ?: return Result.failure(Exception("Failed to save photo"))

                val product = Product(
                    id = id,
                    artisanId = artisanId,
                    name = name,
                    category = category,
                    description = description,
                    photoPath = photoPath,
                    price = price,
                    createdAt = System.currentTimeMillis()
                )
                productDao.update(product)
                Result.success(id)
            } else {
                val product = Product(
                    artisanId = artisanId,
                    name = name,
                    category = category,
                    description = description,
                    price = price,
                    createdAt = System.currentTimeMillis()
                )
                val id = productDao.insert(product).toInt()
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all products for an artisan as a Flow.
     */
    fun getProducts(artisanId: Int): Flow<List<Product>> {
        return productDao.getAllByArtisan(artisanId)
    }

    /**
     * Get products filtered by category.
     */
    fun getProductsByCategory(artisanId: Int, category: String): Flow<List<Product>> {
        return productDao.getByCategory(artisanId, category)
    }

    /**
     * Get a single product by ID.
     */
    suspend fun getProductById(id: Int): Product? {
        return productDao.getById(id)
    }

    /**
     * Update a product.
     */
    suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            productDao.update(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete a product and its photo.
     */
    suspend fun deleteProduct(id: Int): Result<Unit> {
        return try {
            val product = productDao.getById(id)
            product?.let {
                // Delete photo file
                if (it.photoPath.isNotEmpty()) {
                    val file = File(it.photoPath)
                    if (file.exists()) file.delete()
                }
            }
            productDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Save product photo to local storage.
     */
    private fun saveProductPhoto(uri: Uri, artisanId: Int, productId: Int): String? {
        return try {
            val photosDir = File(context.filesDir, "photos/products/$artisanId")
            if (!photosDir.exists()) photosDir.mkdirs()

            val photoFile = File(photosDir, "product_$productId.jpg")
            val bitmap = ImageUtils.uriToBitmap(context, uri) ?: return null
            val compressed = ImageUtils.compressImage(bitmap, 1080, 1080)
            ImageUtils.saveBitmapToFile(compressed, photoFile)
            photoFile.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Update product photo (for editing).
     */
    suspend fun updateProductPhoto(uri: Uri, product: Product): Result<String> {
        return try {
            val path = saveProductPhoto(uri, product.artisanId, product.id)
                ?: return Result.failure(Exception("Failed to save photo"))
            productDao.update(product.copy(photoPath = path))
            Result.success(path)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
