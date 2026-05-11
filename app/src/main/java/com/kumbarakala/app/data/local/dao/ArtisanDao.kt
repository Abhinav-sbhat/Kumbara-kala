package com.kumbarakala.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kumbarakala.app.data.model.Artisan
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtisanDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(artisan: Artisan): Long

    @Update
    suspend fun update(artisan: Artisan)

    @Query("SELECT * FROM artisans WHERE id = :id")
    suspend fun getById(id: Int): Artisan?

    @Query("SELECT * FROM artisans WHERE id = :id")
    fun getByIdFlow(id: Int): Flow<Artisan?>

    @Query("SELECT * FROM artisans WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): Artisan?

    @Query("SELECT * FROM artisans WHERE email = :email AND password_hash = :passwordHash LIMIT 1")
    suspend fun getByEmailAndPassword(email: String, passwordHash: String): Artisan?

    @Query("SELECT COUNT(*) FROM artisans WHERE email = :email")
    suspend fun emailExists(email: String): Int
}
