package com.example.proyectologin005d.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// _  es una convencion de nombre para indicar que la variable
// no puede ser utilizada fuera de la clase
// Se utiliza para diferenciar la propiedad interna(private)
// de la publica

class ProductoViewModel: ViewModel(){

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())

    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun guardarProducto(producto:Producto){
        viewModelScope.launch{
            val nuevaLista =_productos.value +producto
          _productos.value =nuevaLista
        }
    }// fin guardar



}