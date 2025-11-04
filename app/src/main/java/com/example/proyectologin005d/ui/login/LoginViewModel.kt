package com.example.proyectologin005d.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    // ⬅️ AQUÍ EL CAMBIO: usar LoginUiState(), no LoginScreen()
    var uiState by mutableStateOf(LoginUiState())
        private set

    // === Handlers de campos ===
    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    // === Submit con callback que entrega username (compatibilidad)
    fun submit(onSuccess: (String) -> Unit) {
        submitInternal(
            onSuccessUsername = onSuccess,
            onSuccessUnit = {}
        )
    }

    // === Submit con callback simple (para navegar)
    fun submit(onSuccess: () -> Unit) {
        submitInternal(
            onSuccessUsername = { _ -> onSuccess() },
            onSuccessUnit = onSuccess
        )
    }

    // === Implementación común ===
    private fun submitInternal(
        onSuccessUsername: (String) -> Unit,
        onSuccessUnit: () -> Unit
    ) {
        if (uiState.isLoading) return

        val username = uiState.username.trim()
        val password = uiState.password

        if (username.isEmpty() || password.isEmpty()) {
            uiState = uiState.copy(error = "Completa usuario y contraseña")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val ok = withContext(Dispatchers.IO) {
                    repo.login(username, password)
                }
                if (ok) {
                    uiState = uiState.copy(isLoading = false, error = null)
                    onSuccessUsername(username)
                    onSuccessUnit()
                } else {
                    uiState = uiState.copy(isLoading = false, error = "Credenciales inválidas")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Error inesperado"
                )
            }
        }
    }
}
