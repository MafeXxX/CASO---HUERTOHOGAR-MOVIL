// app/src/main/java/com/example/proyectologin005d/data/repository/AuthRepository.kt
package com.example.proyectologin005d.data.repository

import kotlinx.coroutines.delay

class AuthRepository {

    private val demoEmail = "cliente@huerto.cl"
    private val demoPass  = "Huerto2025!"

    /**
     * Retorna true sólo si coincide con las credenciales demo.
     * - Email se compara case-insensitive
     * - Password es case-sensitive
     */
    suspend fun login(email: String, password: String): Boolean {
        // Simula latencia de red (opcional)
        delay(600)
        return email.equals(demoEmail, ignoreCase = true) && password == demoPass
    }
}
