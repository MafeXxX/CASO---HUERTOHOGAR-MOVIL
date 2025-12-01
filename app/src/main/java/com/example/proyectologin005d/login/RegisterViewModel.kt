package com.example.proyectologin005d.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.User
import com.example.proyectologin005d.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = UserRepository(app)

    // Ahora sí es estado observable por Compose
    var state by mutableStateOf(RegisterUiState())
        private set

    fun onNameChange(v: String) {
        state = state.copy(name = v, error = null, success = false)
    }

    fun onEmailChange(v: String) {
        state = state.copy(email = v, error = null, success = false)
    }

    fun onPasswordChange(v: String) {
        state = state.copy(password = v, error = null, success = false)
    }

    fun onConfirmChange(v: String) {
        state = state.copy(confirmPassword = v, error = null, success = false)
    }

    fun register() {
        val name = state.name.trim()
        val email = state.email.trim()
        val pass = state.password
        val confirm = state.confirmPassword

        if (name.isBlank() || email.isBlank() || pass.isBlank() || confirm.isBlank()) {
            state = state.copy(error = "Todos los campos son obligatorios")
            return
        }

        if (pass.length < 8) {
            state = state.copy(error = "La contraseña debe tener al menos 8 caracteres")
            return
        }

        if (pass != confirm) {
            state = state.copy(error = "Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            val ok = repo.registerUser(
                User(
                    email = email,
                    password = pass,
                    name = name
                )
            )

            state = if (ok) {
                state.copy(isLoading = false, success = true)
            } else {
                state.copy(isLoading = false, error = "El correo ya está registrado")
            }
        }
    }
}
