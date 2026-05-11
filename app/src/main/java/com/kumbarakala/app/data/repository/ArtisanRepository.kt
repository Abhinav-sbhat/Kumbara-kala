package com.kumbarakala.app.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.kumbarakala.app.data.local.dao.ArtisanDao
import com.kumbarakala.app.data.local.dao.ProductDao
import com.kumbarakala.app.data.local.dao.StoryCardDao
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.ArtisanStats
import com.kumbarakala.app.util.ImageUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtisanRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val artisanDao: ArtisanDao,
    private val productDao: ProductDao,
    private val storyCardDao: StoryCardDao
) {

    /**
     * Get artisan profile by ID.
     */
    suspend fun getProfile(id: Int): Artisan? {
        return artisanDao.getById(id)
    }

    /**
     * Get artisan profile as Flow for real-time updates.
     */
    fun getProfileFlow(id: Int): Flow<Artisan?> {
        return artisanDao.getByIdFlow(id)
    }

    /**
     * Update artisan profile.
     */
    suspend fun updateProfile(artisan: Artisan): Result<Unit> {
        return try {
            artisanDao.update(artisan)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Save profile photo to local storage and update artisan record.
     */
    suspend fun saveProfilePhoto(uri: Uri, artisanId: Int): Result<String> {
        return try {
            val photosDir = File(context.filesDir, "photos/profiles")
            if (!photosDir.exists()) photosDir.mkdirs()

            val photoFile = File(photosDir, "profile_$artisanId.jpg")
            val bitmap = ImageUtils.uriToBitmap(context, uri)
                ?: return Result.failure(Exception("Failed to load image"))

            val compressed = ImageUtils.compressImage(bitmap, 800, 800)
            ImageUtils.saveBitmapToFile(compressed, photoFile)

            val path = photoFile.absolutePath
            val artisan = artisanDao.getById(artisanId)
            artisan?.let {
                artisanDao.update(it.copy(profilePhoto = path))
            }

            Result.success(path)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get artisan statistics: products count, cards generated, total shares.
     */
    suspend fun getStats(artisanId: Int): ArtisanStats {
        return ArtisanStats(
            productsCount = productDao.getCount(artisanId),
            cardsGenerated = storyCardDao.getCount(artisanId),
            cardsShared = storyCardDao.getTotalShares(artisanId)
        )
    }
}
