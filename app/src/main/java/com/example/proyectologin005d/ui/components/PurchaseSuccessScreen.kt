package com.example.proyectologin005d.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// COMPRA EXITOSA
@Composable
fun PurchaseSuccessScreen(
    onContinue: () -> Unit,
    onBuy: () -> Unit
) {
    LaunchedEffect(Unit) {
        onBuy()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("¡Compra realizada con éxito!", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onContinue) {
                Text("Continuar")
            }
        }
    }
}

