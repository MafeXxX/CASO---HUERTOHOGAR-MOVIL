package com.example.proyectologin005d.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.proyectologin005d.R

// SEGUNDA PANTALLA DE CARGA, QUERIA OCUPAR LA MISMA PERO NO PUDE DOS VECES,
// POR ESO 2 PANTALLA DE CARGA

@Composable
fun LoadingPurchaseScreen(
    onFinished: () -> Unit,
    loadingTime: Long = 600L // puedes modificar el tiempo aquí
) {
    val infinite = rememberInfiniteTransition(label = "purchase-rotate")
    val angle by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "purchase-angle"
    )

    LaunchedEffect(Unit) {
        delay(loadingTime)
        onFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.apple),
            contentDescription = "Cargando compra",
            modifier = Modifier
                .size(160.dp)
                .rotate(angle)
        )
    }
}
