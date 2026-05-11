package com.kumbarakala.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kumbarakala.app.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Int): Product?

    @Query("SELECT * FROM products WHERE artisan_id = :artisanId ORDER BY created_at DESC")
    fun getAllByArtisan(artisanId: Int): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE artisan_id = :artisanId AND category = :category ORDER BY created_at DESC")
    fun getByCategory(artisanId: Int, category: String): Flow<List<Product>>

    @Query("SELECT COUNT(*) FROM products WHERE artisan_id = :artisanId")
    suspend fun getCount(artisanId: Int): Int
}
