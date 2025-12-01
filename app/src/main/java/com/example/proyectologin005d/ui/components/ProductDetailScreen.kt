package com.example.proyectologin005d.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectologin005d.data.FakeCatalog
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.R
import kotlinx.coroutines.launch

// DETALLE DEL PRODUCTO
@Composable
fun ProductDetailScreen(
    productId: String,
    carritoViewModel: CarritoViewModel,
    onAddToCart: (Producto) -> Unit,
    onBack: () -> Unit
) {
    val producto = FakeCatalog.items.find { it.id == productId }
    if (producto == null) {
        Text("Producto no encontrado")
        return
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //  Cantidad empieza en 0 (como pediste)
    var cantidad by remember { mutableStateOf(0) }

    // 🟢 Stock reactivo: resta lo que ya está en el carrito
    val enCarrito = carritoViewModel.countInCart(producto.id)
    val stockBase = producto.stock - enCarrito

    // 🟡 Stock disponible cambia cuando cambia "cantidad"
    val stockDisponible = stockBase - cantidad

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
            }

            val img = if (producto.imageRes != 0)
                painterResource(producto.imageRes)
            else
                painterResource(R.drawable.logo_huerto)

            Image(
                painter = img,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(producto.nombre, style = MaterialTheme.typography.headlineMedium)
            Text("Precio: ${formatoCLP(producto.precio)}")
            Spacer(Modifier.height(12.dp))

            Text("Categoría: ${producto.categoria ?: "Sin categoría"}")

            //  Stock actualizado en tiempo real
            Text("Stock disponible: $stockDisponible")

            Spacer(Modifier.height(20.dp))

            //  Selector de cantidad
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = {
                    if (cantidad > 0) cantidad--
                }) {
                    Icon(Icons.Default.Remove, "Restar")
                }

                Text(cantidad.toString(), fontSize = 20.sp)

                IconButton(onClick = {
                    if (cantidad < stockBase) cantidad++
                    else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "No hay más stock disponible"
                            )
                        }
                    }
                }) {
                    Icon(Icons.Default.Add, "Sumar")
                }
            }

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                    if (cantidad <= 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Seleccione una cantidad")
                        }
                        return@Button
                    }

                    val cantidadAgregada = cantidad  // ← guardar valor actual

                    repeat(cantidadAgregada) { onAddToCart(producto) }

                    scope.launch {
                        snackbarHostState.showSnackbar("Agregado $cantidadAgregada al carrito")
                    }

                    cantidad = 0 // ← reiniciar después de usar
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = stockBase > 0
            ) {
                Text("Agregar al carrito")
            }

        }
    }
}







