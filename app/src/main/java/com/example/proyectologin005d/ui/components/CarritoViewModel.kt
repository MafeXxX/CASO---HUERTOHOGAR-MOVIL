package com.example.proyectologin005d.ui.components

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.data.repository.CompraRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// EL VIEWMODEL DEL carrito
data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
)

class CarritoViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Repositorio para registrar compras en la BDD
    private val compraRepository = CompraRepository(application)

    private val _cart = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val cart: StateFlow<List<ItemCarrito>> = _cart.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    val products = _products.asStateFlow()

    // ---------------------------------------------------------------------
    // AGREGAR AL CARRITO
    // ---------------------------------------------------------------------
    fun addToCart(producto: Producto) {
        val items = _cart.value.toMutableList()
        val index = items.indexOfFirst { it.producto.id == producto.id }

        if (index >= 0) {
            val current = items[index]

            if (current.cantidad >= producto.stock) {
                viewModelScope.launch {
                    _events.emit("No puedes agregar más. Stock máximo alcanzado.")
                }
                return
            }

            items[index] = current.copy(cantidad = current.cantidad + 1)

        } else {
            items.add(ItemCarrito(producto, 1))
        }

        _cart.value = items

        viewModelScope.launch {
            _events.emit("Producto agregado: ${producto.nombre}")
        }
    }

    // ---------------------------------------------------------------------
    // INCREMENTAR CANTIDAD
    // ---------------------------------------------------------------------
    fun increaseQuantity(producto: Producto) {
        val items = _cart.value.toMutableList()
        val index = items.indexOfFirst { it.producto.id == producto.id }

        if (index < 0) return

        val current = items[index]

        if (current.cantidad >= producto.stock) {
            viewModelScope.launch {
                _events.emit("No puedes agregar más. Stock máximo alcanzado.")
            }
            return
        }

        items[index] = current.copy(cantidad = current.cantidad + 1)
        _cart.value = items
    }

    // ---------------------------------------------------------------------
    // REDUCIR CANTIDAD (si queda 0, eliminar)
    // ---------------------------------------------------------------------
    fun decreaseQuantity(producto: Producto) {
        val items = _cart.value.toMutableList()
        val index = items.indexOfFirst { it.producto.id == producto.id }

        if (index < 0) return

        val current = items[index]

        if (current.cantidad <= 1) {
            items.removeAt(index)
        } else {
            items[index] = current.copy(cantidad = current.cantidad - 1)
        }

        // chiste interno: no tocamos el stock realmente
        producto.stock += 0

        _cart.value = items
    }

    // ---------------------------------------------------------------------
    // ELIMINAR TODO EL CARRITO
    // ---------------------------------------------------------------------
    fun clearCart() {
        val cartSnapshot = _cart.value.toList()

        val updatedProducts = _products.value.map { product ->
            val cartItem = cartSnapshot.find { it.producto.id == product.id }
            if (cartItem != null) {
                product.copy(stock = product.stock + cartItem.cantidad)
            } else {
                product
            }
        }

        _products.value = updatedProducts
        _cart.value = emptyList()
    }

    // ---------------------------------------------------------------------
    // TOTAL A PAGAR
    // ---------------------------------------------------------------------
    fun getTotal(): Int {
        return _cart.value.sumOf { it.producto.precio * it.cantidad }
    }

    fun totalCarrito(): Int = getTotal()

    // ---------------------------------------------------------------------
    // CONTAR UNIDADES DE UN PRODUCTO
    // ---------------------------------------------------------------------
    fun countInCart(productId: String): Int {
        return _cart.value.firstOrNull { it.producto.id == productId }?.cantidad ?: 0
    }

    // ---------------------------------------------------------------------
    // COMPRAR → GUARDA EN LA BDD CON EL USUARIO
    // ---------------------------------------------------------------------
    fun comprar(userEmail: String) {
        viewModelScope.launch {
            if (_cart.value.isEmpty()) {
                _events.emit("El carrito está vacío")
                return@launch
            }

            val items = _cart.value
            val total = items.sumOf { it.producto.precio * it.cantidad }
            val cantidadProductos = items.sumOf { it.cantidad }

            // 1) Registrar la compra en la base de datos
            compraRepository.registrarCompra(
                userEmail = userEmail,
                total = total,
                cantidadProductos = cantidadProductos
            )

            // 2) Restar stock en los productos (si quieres)
            items.forEach { item ->
                item.producto.stock -= item.cantidad
            }

            // 3) Vaciar el carrito
            _cart.value = emptyList()

            // 4) Avisar a la UI
            _events.emit("Compra realizada y guardada en la BDD")
        }
    }
}
