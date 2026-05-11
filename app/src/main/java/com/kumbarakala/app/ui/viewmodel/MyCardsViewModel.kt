package com.kumbarakala.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.data.repository.AuthRepository
import com.kumbarakala.app.data.repository.CardRepository
import com.kumbarakala.app.util.ShareHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCardsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cardRepository: CardRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _artisanId = MutableStateFlow<Int?>(null)

    val cards: StateFlow<List<StoryCard>> = _artisanId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList())
        else cardRepository.getCards(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadArtisan()
    }

    private fun loadArtisan() {
        viewModelScope.launch {
            _artisanId.value = authRepository.getCurrentArtisanId()
        }
    }

    fun deleteCard(cardId: Int) {
        viewModelScope.launch {
            cardRepository.deleteCard(cardId)
        }
    }

    fun shareCard(card: StoryCard) {
        if (card.cardImagePath.isNotEmpty()) {
            ShareHelper.shareToWhatsApp(
                context,
                card.cardImagePath,
                "Check out this handcrafted product from Kumbara-Kala!"
            )
            viewModelScope.launch {
                cardRepository.incrementShareCount(card.id)
            }
        }
    }
}
