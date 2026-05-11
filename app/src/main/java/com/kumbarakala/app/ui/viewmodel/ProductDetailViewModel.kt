package com.kumbarakala.app.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.data.repository.ArtisanRepository
import com.kumbarakala.app.data.repository.AuthRepository
import com.kumbarakala.app.data.repository.CardRepository
import com.kumbarakala.app.data.repository.ProductRepository
import com.kumbarakala.app.engine.CardRenderer
import com.kumbarakala.app.util.ImageUtils
import com.kumbarakala.app.util.ShareHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailUiState(
    val product: Product? = null,
    val artisan: Artisan? = null,
    val generatedCard: StoryCard? = null,
    val cardBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val selectedTemplate: String = "Classic",
    val error: String? = null,
    val shareSuccess: Boolean = false,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productRepository: ProductRepository,
    private val cardRepository: CardRepository,
    private val artisanRepository: ArtisanRepository,
    private val authRepository: AuthRepository,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val product = productRepository.getProductById(productId)
            val artisanId = authRepository.getCurrentArtisanId()
            val artisan = artisanId?.let { artisanRepository.getProfile(it) }

            _uiState.value = _uiState.value.copy(
                product = product,
                artisan = artisan,
                isLoading = false
            )
        }
    }

    fun setTemplate(style: String) {
        _uiState.value = _uiState.value.copy(selectedTemplate = style)

        // Re-render card if we already have generated text
        val card = _uiState.value.generatedCard
        if (card != null) {
            renderCardWithTemplate(card.copy(templateStyle = style))
        }
    }

    fun generateCard() {
        val product = _uiState.value.product ?: return
        val artisan = _uiState.value.artisan ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, error = null)

            try {
                // Parallel AI calls for benefit text and health fact
                val benefitDeferred = async { generateBenefitText(product) }
                val healthDeferred = async { generateHealthFact(product) }

                val benefitText = benefitDeferred.await()
                val healthFact = healthDeferred.await()

                // Create story card
                val storyCard = StoryCard(
                    productId = product.id,
                    artisanId = artisan.id,
                    benefitText = benefitText,
                    healthFact = healthFact,
                    templateStyle = _uiState.value.selectedTemplate,
                    createdAt = System.currentTimeMillis()
                )

                // Save to database
                val cardIdResult = cardRepository.saveCard(storyCard)
                val cardId = cardIdResult.getOrNull() ?: 0
                val savedCard = storyCard.copy(id = cardId)

                // Render the card image
                val productPhoto = if (product.photoPath.isNotEmpty()) {
                    ImageUtils.loadBitmapFromPath(product.photoPath)
                } else null

                val cardBitmap = CardRenderer.renderCard(productPhoto, product, savedCard, artisan)

                // Save card image and get the actual file path
                val imagePathResult = cardRepository.saveCardImage(cardBitmap, artisan.id, cardId)
                val imagePath = imagePathResult.getOrNull() ?: ""

                // Update the card with the actual image path in state
                val cardWithPath = savedCard.copy(cardImagePath = imagePath)

                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    generatedCard = cardWithPath,
                    cardBitmap = cardBitmap
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "Failed to generate card: ${e.message}"
                )
            }
        }
    }

    private suspend fun generateBenefitText(product: Product): String {
        return try {
            val prompt = """You are a marketing expert for traditional Indian clay products.
Generate a short, compelling benefit story (2-3 sentences) for this clay product that highlights its health and eco-friendly advantages.
Make it sound premium and educational.
Product: ${product.name}, Category: ${product.category}, Description: ${product.description}
Include one specific health or scientific fact."""

            val response = generativeModel.generateContent(prompt)
            response.text?.trim() ?: getDefaultBenefitText(product)
        } catch (e: Exception) {
            getDefaultBenefitText(product)
        }
    }

    private suspend fun generateHealthFact(product: Product): String {
        return try {
            val prompt = """Give ONE short health fact (maximum 10 words) about using a clay ${product.category} called ${product.name}. Example format: 'Maintains natural pH balance in food'. Return just the fact, nothing else."""

            val response = generativeModel.generateContent(prompt)
            response.text?.trim() ?: getDefaultHealthFact(product)
        } catch (e: Exception) {
            getDefaultHealthFact(product)
        }
    }

    private fun getDefaultBenefitText(product: Product): String {
        return "Handcrafted with care using traditional techniques, this ${product.name} brings the wisdom of ancient artisanship to your home. Made from natural clay, it preserves the earth's nutrients while adding rustic charm to your daily life."
    }

    private fun getDefaultHealthFact(product: Product): String {
        return "Natural clay retains essential minerals"
    }

    private fun renderCardWithTemplate(card: StoryCard) {
        val product = _uiState.value.product ?: return
        val artisan = _uiState.value.artisan ?: return

        viewModelScope.launch {
            val productPhoto = if (product.photoPath.isNotEmpty()) {
                ImageUtils.loadBitmapFromPath(product.photoPath)
            } else null

            val cardBitmap = CardRenderer.renderCard(productPhoto, product, card, artisan)

            // Save updated card
            cardRepository.updateCard(card)
            val imagePathResult = cardRepository.saveCardImage(cardBitmap, artisan.id, card.id)
            val imagePath = imagePathResult.getOrNull() ?: card.cardImagePath

            // Update the card with image path
            val cardWithPath = card.copy(cardImagePath = imagePath)

            _uiState.value = _uiState.value.copy(
                generatedCard = cardWithPath,
                cardBitmap = cardBitmap
            )
        }
    }

    fun shareOnWhatsApp() {
        val card = _uiState.value.generatedCard ?: return
        if (card.cardImagePath.isEmpty()) return

        val success = ShareHelper.shareToWhatsApp(
            context,
            card.cardImagePath,
            "Check out this handcrafted product from Kumbara-Kala! 🏺"
        )

        if (success) {
            viewModelScope.launch {
                cardRepository.incrementShareCount(card.id)
            }
            _uiState.value = _uiState.value.copy(shareSuccess = true)
        }
    }

    fun shareToOtherApps() {
        val card = _uiState.value.generatedCard ?: return
        if (card.cardImagePath.isEmpty()) return

        ShareHelper.shareToOtherApps(
            context,
            card.cardImagePath,
            "Check out this handcrafted product from Kumbara-Kala! 🏺"
        )

        viewModelScope.launch {
            cardRepository.incrementShareCount(card.id)
        }
    }

    fun saveToGallery() {
        val card = _uiState.value.generatedCard ?: return
        val product = _uiState.value.product ?: return
        if (card.cardImagePath.isEmpty()) return

        val success = ShareHelper.saveToGallery(context, card.cardImagePath, product.name)
        _uiState.value = _uiState.value.copy(saveSuccess = success)
    }

    fun deleteProduct() {
        val product = _uiState.value.product ?: return
        viewModelScope.launch {
            productRepository.deleteProduct(product.id)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessFlags() {
        _uiState.value = _uiState.value.copy(shareSuccess = false, saveSuccess = false)
    }
}
