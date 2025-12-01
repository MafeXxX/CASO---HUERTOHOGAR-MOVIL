package com.example.proyectologin005d.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("users_db")

class UserRepository(private val context: Context) {

    private val USERS_KEY = stringSetPreferencesKey("users")

    suspend fun registerUser(user: User): Boolean {
        val currentUsers = getAllUsers().toMutableSet()

        // Si ya existe el correo, no se registra
        if (currentUsers.any { it.email == user.email }) return false

        currentUsers.add(user)
        saveUsers(currentUsers)
        return true
    }

    suspend fun login(email: String, password: String): Boolean {
        val users = getAllUsers()
        return users.any { it.email == email && it.password == password }
    }

    suspend fun getAllUsers(): Set<User> {
        val prefs = context.dataStore.data.first()
        val stored = prefs[USERS_KEY] ?: emptySet()

        return stored.map { linea ->
            val parts = linea.split("|")
            User(parts[0], parts[1], parts[2])
        }.toSet()
    }

    private suspend fun saveUsers(users: Set<User>) {
        context.dataStore.edit { prefs ->
            prefs[USERS_KEY] = users.map { "${it.email}|${it.password}|${it.name}" }.toSet()
        }
    }
}
