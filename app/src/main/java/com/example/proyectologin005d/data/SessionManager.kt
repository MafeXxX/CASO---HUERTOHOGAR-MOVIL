package com.example.proyectologin005d.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// DataStore
private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_EMAIL = stringPreferencesKey("user_email")
        private val KEY_PHOTO_URI = stringPreferencesKey("profile_photo_uri")
    }

    // ============================================================
    // EMAIL
    // ============================================================
    val emailFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_EMAIL]
    }

    suspend fun setEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
        }
    }

    suspend fun getEmailOnce(): String? {
        return emailFlow.first()
    }

    // ============================================================
    // FOTO DE PERFIL (URI)
    // ============================================================
    val photoUriFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_PHOTO_URI]
    }

    suspend fun setPhotoUri(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_PHOTO_URI] = uri
        }
    }

    // ============================================================
    // LIMPIAR SESIÓN (logout)
    // ============================================================
    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_EMAIL)
            prefs.remove(KEY_PHOTO_URI)
        }
    }
}
