package com.example.proyectologin005d.ui.components

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.proyectologin005d.data.model.Producto
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarritoViewModelTest {

    @Test
    fun addToCart_agrega_el_producto_y_actualiza_cantidad_y_total() {
        val viewModel = CarritoViewModel()

        val producto = Producto(
            id = "PR001",
            nombre = "Manzana Fuji",
            precio = 1000,
            imagenUrl = null,
            categoria = "Frutas",
            stock = 5,
            imageRes = 0
        )

        // Ejecutamos en el hilo principal de Android
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            viewModel.addToCart(producto)
            viewModel.addToCart(producto)
        }

        val cart = viewModel.cart.value
        val total = viewModel.getTotal()
        val cantidadEnCarrito = viewModel.countInCart("PR001")

        assertEquals(1, cart.size)
        assertEquals("PR001", cart[0].producto.id)
        assertEquals(2, cart[0].cantidad)
        assertEquals(2000, total)
        assertEquals(2, cantidadEnCarrito)
    }
}