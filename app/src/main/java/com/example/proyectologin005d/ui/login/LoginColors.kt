package com.example.proyectologin005d.ui.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class LoginColors(
    val background: Color,
    val title: Color,
    val fieldContainer: Color,
    val fieldText: Color,
    val fieldLabel: Color,
    val fieldBorder: Color,
    val error: Color,
    val buttonContainer: Color,
    val buttonContent: Color
)

object LoginDefaults {

    /** Toma colores desde tu MaterialTheme (modo claro/oscuro). */
    @Composable
    fun fromTheme(): LoginColors {
        val cs = MaterialTheme.colorScheme
        return LoginColors(
            background = cs.background,
            title = cs.onBackground,
            fieldContainer = cs.surface,
            fieldText = cs.onSurface,
            fieldLabel = cs.onSurfaceVariant,
            fieldBorder = cs.outline,
            error = cs.error,
            buttonContainer = cs.primary,
            buttonContent = cs.onPrimary
        )
    }

    /** Paleta fija (marca Huerto Hogar). Cambia hex si quieres. */
    fun brand(): LoginColors = LoginColors(
        background = Color(0xFFF7F7F7),
        title = Color(0xFF333333),
        fieldContainer = Color.White,
        fieldText = Color(0xFF333333),
        fieldLabel = Color(0xFF666666),
        fieldBorder = Color(0xFFDDDDDD),
        error = Color(0xFFB00020),
        buttonContainer = Color(0xFF2E8B57),
        buttonContent = Color.White
    )
}
