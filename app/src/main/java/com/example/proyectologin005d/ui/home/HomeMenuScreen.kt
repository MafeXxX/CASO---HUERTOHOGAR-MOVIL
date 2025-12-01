package com.example.proyectologin005d.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeMenuScreen(
    onGoToCatalog: () -> Unit,
    onLogout: () -> Unit,
    onGoToProfile: () -> Unit,      // navegación al perfil
    colors: HomeMenuColors? = null  // paleta inyectable
) {
    val palette = colors ?: HomeDefaults.menuFromTheme()

    Surface(Modifier.fillMaxSize(), color = palette.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Huerto Hogar", color = palette.title)
            Spacer(Modifier.height(24.dp))

            // Catálogo
            Button(
                onClick = onGoToCatalog,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.buttonContainer,
                    contentColor = palette.buttonContent
                )
            ) { Text("Ir al Catálogo") }

            Spacer(Modifier.height(12.dp))

            // Mi Perfil
            Button(
                onClick = onGoToProfile,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.buttonContainer,
                    contentColor = palette.buttonContent
                )
            ) { Text("Mi Perfil") }

            Spacer(Modifier.height(12.dp))

            // Cerrar sesión
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.buttonContainer,
                    contentColor = palette.buttonContent
                )
            ) { Text("Cerrar sesión") }
        }
    }
}

// comentario, tecnicamente nada funciona,
// porque no esta conectado a algun lado