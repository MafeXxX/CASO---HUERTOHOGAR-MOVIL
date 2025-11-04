// app/src/main/java/com/example/proyectologin005d/ui/login/LoginUiState.kt
package com.example.proyectologin005d.ui.login

data class LoginUiState(
    // Pre-rellenado para desarrollo/pruebas
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
