package com.example.proyectologin005d.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey val id: String,
    val nombre: String,
    val precio: Int,
    val imagenUrl: String? = null,
    val categoria: String? = null,
    var stock: Int = 0,
    // Opción A: id de recurso de drawable
    val imageRes: Int = 0
)
