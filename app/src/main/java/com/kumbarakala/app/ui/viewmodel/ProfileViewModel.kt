package com.kumbarakala.app.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.ArtisanStats
import com.kumbarakala.app.data.repository.ArtisanRepository
import com.kumbarakala.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val artisan: Artisan? = null,
    val stats: ArtisanStats? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val artisanRepository: ArtisanRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val artisanId = authRepository.getCurrentArtisanId()
            if (artisanId != null) {
                val artisan = artisanRepository.getProfile(artisanId)
                val stats = artisanRepository.getStats(artisanId)
                _uiState.value = _uiState.value.copy(
                    artisan = artisan,
                    stats = stats,
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun updateProfile(
        name: String,
        phone: String,
        village: String,
        bio: String,
        experience: String,
        specialty: String
    ) {
        val current = _uiState.value.artisan ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, error = null)
            val updated = current.copy(
                name = name.trim(),
                phone = phone.trim(),
                village = village.trim(),
                bio = bio.trim(),
                experience = experience.trim(),
                specialty = specialty.trim()
            )

            val result = artisanRepository.updateProfile(updated)
            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        artisan = updated,
                        isSaving = false,
                        saveSuccess = true
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = e.message ?: "Failed to save"
                    )
                }
            )
        }
    }

    fun uploadPhoto(uri: Uri) {
        val artisan = _uiState.value.artisan ?: return

        viewModelScope.launch {
            val result = artisanRepository.saveProfilePhoto(uri, artisan.id)
            result.fold(
                onSuccess = { path ->
                    _uiState.value = _uiState.value.copy(
                        artisan = artisan.copy(profilePhoto = path)
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to upload photo"
                    )
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun clearSuccessFlag() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
