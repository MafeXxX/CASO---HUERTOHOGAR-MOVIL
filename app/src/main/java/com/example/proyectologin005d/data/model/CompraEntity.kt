package com.example.proyectologin005d.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compra")
data class CompraEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // correo del usuario que hizo la compra
    val userEmail: String,

    // total en pesos (puedes cambiar a Double si quieres)
    val total: Int,

    // cantidad total de productos en esa compra
    val cantidadProductos: Int,

    // timestamp de la compra (System.currentTimeMillis())
    val fechaMillis: Long
)
