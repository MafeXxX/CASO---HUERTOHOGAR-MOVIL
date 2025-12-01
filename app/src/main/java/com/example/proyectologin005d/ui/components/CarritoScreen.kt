package com.example.proyectologin005d.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.proyectologin005d.ui.splash.LoadingPurchaseScreen

// EL CARRITO

@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel,
    onBack: () -> Unit,
    onBuyClick: () -> Unit
) {
    val carrito = carritoViewModel.cart.collectAsState().value
    val total = carritoViewModel.getTotal()

    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    // ➜ Snackbar para mostrar errores o avisos
    val snackbarHostState = remember { SnackbarHostState() }

    // Escucha los eventos del ViewModel
    LaunchedEffect(Unit) {
        carritoViewModel.events.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    when {
        showLoading -> {
            LoadingPurchaseScreen(
                onFinished = {
                    carritoViewModel.comprar()
                    showLoading = false
                    showSuccess = true
                }
            )
        }

        showSuccess -> {
            PurchaseSuccessScreen(
                onContinue = {

                    showSuccess = false
                },
                onBuy = {
                    carritoViewModel.comprar()   // o lo que quieras ejecutar
                }
            )
        }

        else -> {
            Scaffold (
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                        Text("Mi carrito", style = MaterialTheme.typography.titleLarge)
                    }

                    Spacer(Modifier.height(12.dp))

                    if (carrito.isEmpty()) {
                        Text("Tu carrito está vacío.")
                    } else {
                        carrito.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = rememberAsyncImagePainter(item.producto.imageRes),
                                    contentDescription = item.producto.nombre,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Spacer(Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        "${formatoCLP(item.producto.precio)} × ${item.cantidad} unidades",
                                        style = MaterialTheme.typography.bodyMedium
                                    )


                                }



                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    IconButton(onClick = {
                                        carritoViewModel.decreaseQuantity(item.producto)
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Restar")
                                    }

                                    Text(
                                        "${item.cantidad}",
                                        modifier = Modifier.width(24.dp),
                                        textAlign = TextAlign.Center
                                    )

                                    IconButton(onClick = {
                                        carritoViewModel.increaseQuantity(item.producto)
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Sumar")
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "Total: ${formatoCLP(total)}",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(onClick = { carritoViewModel.clearCart() }) {
                            Text("Vaciar Carrito")
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                showLoading = true
                                onBuyClick()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Comprar")
                        }
                    }
                }
            }
        }
    }
}





