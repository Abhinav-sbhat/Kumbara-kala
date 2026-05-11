package com.kumbarakala.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kumbarakala.app.data.model.StoryCard
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storyCard: StoryCard): Long

    @Update
    suspend fun update(storyCard: StoryCard)

    @Query("DELETE FROM story_cards WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM story_cards WHERE id = :id")
    suspend fun getById(id: Int): StoryCard?

    @Query("SELECT * FROM story_cards WHERE artisan_id = :artisanId ORDER BY created_at DESC")
    fun getAllByArtisan(artisanId: Int): Flow<List<StoryCard>>

    @Query("SELECT * FROM story_cards WHERE product_id = :productId ORDER BY created_at DESC")
    fun getByProductId(productId: Int): Flow<List<StoryCard>>

    @Query("UPDATE story_cards SET share_count = share_count + 1 WHERE id = :id")
    suspend fun incrementShareCount(id: Int)

    @Query("SELECT COUNT(*) FROM story_cards WHERE artisan_id = :artisanId")
    suspend fun getCount(artisanId: Int): Int

    @Query("SELECT COALESCE(SUM(share_count), 0) FROM story_cards WHERE artisan_id = :artisanId")
    suspend fun getTotalShares(artisanId: Int): Int
}
