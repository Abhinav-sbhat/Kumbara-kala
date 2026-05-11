package com.kumbarakala.app.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumbarakala.app.data.repository.AuthRepository
import com.kumbarakala.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddProductUiState(
    val photoUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    fun setPhoto(uri: Uri) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }

    fun saveProduct(
        name: String,
        category: String,
        description: String,
        price: String
    ) {
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Product name is required")
            return
        }
        if (category.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Please select a category")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val artisanId = authRepository.getCurrentArtisanId()
            if (artisanId == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Not logged in"
                )
                return@launch
            }

            val result = productRepository.addProduct(
                artisanId = artisanId,
                name = name.trim(),
                category = category.lowercase().trim(),
                description = description.trim(),
                price = price.trim(),
                photoUri = _uiState.value.photoUri
            )

            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSaved = true
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to save product"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun reset() {
        _uiState.value = AddProductUiState()
    }
}
