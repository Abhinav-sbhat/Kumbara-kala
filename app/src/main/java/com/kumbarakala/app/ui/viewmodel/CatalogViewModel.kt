package com.kumbarakala.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.data.repository.AuthRepository
import com.kumbarakala.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _artisanId = MutableStateFlow<Int?>(null)
    private val _artisanName = MutableStateFlow("")
    val artisanName: StateFlow<String> = _artisanName.asStateFlow()

    val products: StateFlow<List<Product>> = _artisanId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList())
        else _selectedCategory.flatMapLatest { category ->
            if (category == "All") productRepository.getProducts(id)
            else productRepository.getProductsByCategory(id, category.lowercase())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadArtisan()
    }

    private fun loadArtisan() {
        viewModelScope.launch {
            val artisan = authRepository.getCurrentArtisan()
            artisan?.let {
                _artisanId.value = it.id
                _artisanName.value = it.name
            }
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.deleteProduct(productId)
        }
    }

    fun getArtisanId(): Int? = _artisanId.value
}
