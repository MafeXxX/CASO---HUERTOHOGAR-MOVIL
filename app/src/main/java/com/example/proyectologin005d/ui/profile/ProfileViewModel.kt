package com.example.proyectologin005d.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ProfileUiState(
    val email: String = "",
    val photoUri: String? = null
)

class ProfileViewModel(private val session: SessionManager) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(session.emailFlow, session.photoUriFlow) { email, photo ->
                ProfileUiState(email = email.orEmpty(), photoUri = photo)
            }.collect { _state.value = it }
        }
    }

    fun savePhoto(uri: String) {
        viewModelScope.launch {
            session.setPhotoUri(uri)
        }
    }
}
