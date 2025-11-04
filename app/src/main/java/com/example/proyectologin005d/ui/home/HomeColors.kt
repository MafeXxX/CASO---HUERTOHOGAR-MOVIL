package com.example.proyectologin005d.ui.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/* ===== Menú (pantalla intermedia: botones grandes) ===== */
data class HomeMenuColors(
    val background: Color,
    val title: Color,
    val buttonContainer: Color,
    val buttonContent: Color,
)

/* ===== Catálogo (lista) ===== */
data class CatalogColors(
    val background: Color,
    val title: Color,
    val text: Color,
    val error: Color,
    val progress: Color
)

object HomeDefaults {
    /* ---- Desde el tema actual (claro/oscuro) ---- */
    @Composable fun menuFromTheme(): HomeMenuColors {
        val cs = MaterialTheme.colorScheme
        return HomeMenuColors(
            background = cs.background,
            title = cs.onBackground,
            buttonContainer = cs.primary,
            buttonContent = cs.onPrimary
        )
    }
    @Composable fun catalogFromTheme(): CatalogColors {
        val cs = MaterialTheme.colorScheme
        return CatalogColors(
            background = cs.background,
            title = cs.onBackground,
            text = cs.onSurface,
            error = cs.error,
            progress = cs.primary
        )
    }

    /* ---- Paleta fija de marca (opcional) ---- */
    fun menuBrand(): HomeMenuColors = HomeMenuColors(
        background = Color(0xFFF7F7F7),
        title = Color(0xFF333333),
        buttonContainer = Color(0xFF2E8B57),
        buttonContent = Color.White
    )
    fun catalogBrand(): CatalogColors = CatalogColors(
        background = Color(0xFFF7F7F7),
        title = Color(0xFF333333),
        text = Color(0xFF333333),
        error = Color(0xFFB00020),
        progress = Color(0xFF2E8B57)
    )
}
