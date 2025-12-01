package com.example.proyectologin005d.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onGoToCatalog: () -> Unit = {},
    onGoToProfile: () -> Unit = {},   // <- NUEVO
    onGoToCart: () -> Unit,
    onLogout: () -> Unit = {}
) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Huerto Hogar", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))

            Button(onClick = onGoToCatalog, modifier = Modifier.fillMaxWidth()) {
                Text("Ir al Catálogo")
            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = onGoToProfile, modifier = Modifier.fillMaxWidth()) {
                Text("Mi Perfil")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onGoToCart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mi Carrito")
            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                Text("Cerrar sesión")
            }
        }
    }
}
