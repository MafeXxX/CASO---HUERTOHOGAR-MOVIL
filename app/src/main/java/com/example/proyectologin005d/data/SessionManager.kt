package com.example.proyectologin005d.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_EMAIL = stringPreferencesKey("user_email")
        private val KEY_PHOTO_URI = stringPreferencesKey("profile_photo_uri")
    }

    // === EMAIL ===
    val emailFlow: Flow<String?> = context.dataStore.data.map { it[KEY_EMAIL] }

    suspend fun setEmail(email: String) {
        context.dataStore.edit { it[KEY_EMAIL] = email }
    }

    // === FOTO PERFIL (URI en String) ===
    val photoUriFlow: Flow<String?> = context.dataStore.data.map { it[KEY_PHOTO_URI] }

    suspend fun setPhotoUri(uri: String) {
        context.dataStore.edit { it[KEY_PHOTO_URI] = uri }
    }
}
