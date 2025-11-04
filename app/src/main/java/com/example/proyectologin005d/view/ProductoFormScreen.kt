package com.example.proyectologin005d.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.NumberFormat
import java.util.Locale

private fun clp(n: Int): String =
    NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(n)

/**
 * Composable unificado para evitar errores de parámetros.
 * - Desde AppNav.kt viene nombre y precio como String.
 * - Agregamos parámetros opcionales con default para no romper otras llamadas
 *   que usen nombres como cantidad/direccion/etc.
 */
@Composable
fun ProductoFormScreen(
    nombre: String,
    precio: String,
    navController: NavController,
    // opcionales (por compatibilidad con otras llamadas)
    id: String? = null,
    cantidad: Int = 1,
    direccion: String = "",
    conPapas: Boolean = false,
    agrandarBebida: Boolean = false
) {
    // Estados locales del formulario
    var qty by remember { mutableStateOf(cantidad) }
    var addr by remember { mutableStateOf(direccion) }

    // Convertimos precio String -> Int si es posible (solo para mostrar formateado)
    val precioInt = precio.toIntOrNull()

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Producto", style = MaterialTheme.typography.headlineSmall)
            Text(nombre, style = MaterialTheme.typography.titleMedium)

            Text(
                text = if (precioInt != null) clp(precioInt) else precio,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Cantidad
            OutlinedTextField(
                value = qty.toString(),
                onValueChange = { v -> qty = v.toIntOrNull()?.coerceAtLeast(1) ?: 1 },
                label = { Text("Cantidad") },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Dirección (si aplica a tu flujo)
            OutlinedTextField(
                value = addr,
                onValueChange = { addr = it },
                label = { Text("Dirección (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) { Text("Volver") }

                Button(
                    onClick = {
                        // TODO: aquí iría lógica de agregar al carrito / guardar en Room
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Agregar") }
            }

            // Debug/compat info (puedes borrar)
            if (id != null) {
                Text("ID: $id", style = MaterialTheme.typography.labelSmall)
            }
            if (conPapas) {
                Text("Con papas", style = MaterialTheme.typography.labelSmall)
            }
            if (agrandarBebida) {
                Text("Bebida agrandada", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
