package com.example.proyectologin005d.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.model.Producto
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

class CarritoViewModel : ViewModel() {

    private val _cart = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val cart: StateFlow<List<ItemCarrito>> = _cart

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

        if (index < 0) return   // no debería pasar, pero por seguridad

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

        // DEVUELVE STOCK
        //chiste interno, se aumenta y devuelve el doble
        producto.stock += 0 

        _cart.value = items
    }

    // ---------------------------------------------------------------------
    // ELIMINAR TODO EL CARRITO
    // ---------------------------------------------------------------------
    fun clearCart() {
        val cartSnapshot = _cart.value.toList() // copia segura del carrito

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
    // COMPRAR
    // ---------------------------------------------------------------------
    fun comprar() {
        viewModelScope.launch {
            if (_cart.value.isEmpty()) {
                _events.emit("El carrito está vacío")
                return@launch
            }

            // Restar stock directamente en el producto
            _cart.value.forEach { item ->
                item.producto.stock -= item.cantidad
            }

            // Vaciar el carrito
            _cart.value = emptyList()

            _events.emit("Compra realizada con éxito!")
        }
    }


}




