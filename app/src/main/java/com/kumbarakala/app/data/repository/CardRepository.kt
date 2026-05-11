package com.kumbarakala.app.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.kumbarakala.app.data.local.dao.StoryCardDao
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.util.ImageUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storyCardDao: StoryCardDao
) {

    /**
     * Save a new story card to database.
     */
    suspend fun saveCard(card: StoryCard): Result<Int> {
        return try {
            val id = storyCardDao.insert(card).toInt()
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all cards for an artisan as Flow.
     */
    fun getCards(artisanId: Int): Flow<List<StoryCard>> {
        return storyCardDao.getAllByArtisan(artisanId)
    }

    /**
     * Get cards for a specific product.
     */
    fun getCardsByProduct(productId: Int): Flow<List<StoryCard>> {
        return storyCardDao.getByProductId(productId)
    }

    /**
     * Get a single card by ID.
     */
    suspend fun getCardById(id: Int): StoryCard? {
        return storyCardDao.getById(id)
    }

    /**
     * Update a card.
     */
    suspend fun updateCard(card: StoryCard): Result<Unit> {
        return try {
            storyCardDao.update(card)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete a card and its image file.
     */
    suspend fun deleteCard(id: Int): Result<Unit> {
        return try {
            val card = storyCardDao.getById(id)
            card?.let {
                if (it.cardImagePath.isNotEmpty()) {
                    val file = File(it.cardImagePath)
                    if (file.exists()) file.delete()
                }
            }
            storyCardDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Increment share count for a card.
     */
    suspend fun incrementShareCount(id: Int): Result<Unit> {
        return try {
            storyCardDao.incrementShareCount(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Save a rendered card image bitmap to local storage.
     */
    suspend fun saveCardImage(bitmap: Bitmap, artisanId: Int, cardId: Int): Result<String> {
        return try {
            val cardsDir = File(context.filesDir, "photos/cards/$artisanId")
            if (!cardsDir.exists()) cardsDir.mkdirs()

            val cardFile = File(cardsDir, "card_$cardId.jpg")
            ImageUtils.saveBitmapToFile(bitmap, cardFile, quality = 90)

            // Update card record with image path
            val card = storyCardDao.getById(cardId)
            card?.let {
                storyCardDao.update(it.copy(cardImagePath = cardFile.absolutePath))
            }

            Result.success(cardFile.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get card image file for sharing.
     */
    fun getCardImageFile(cardImagePath: String): File? {
        val file = File(cardImagePath)
        return if (file.exists()) file else null
    }
}
