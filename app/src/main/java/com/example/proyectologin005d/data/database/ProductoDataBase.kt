package com.example.proyectologin005d.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectologin005d.data.dao.ProductoDao
import com.example.proyectologin005d.data.dao.ComentarioDao
import com.example.proyectologin005d.data.dao.CompraDao
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.data.model.Comentario
import com.example.proyectologin005d.data.model.CompraEntity

@Database(
    entities = [
        Producto::class,
        Comentario::class,
        CompraEntity::class        // 👈 tabla de compras
    ],
    version = 3,                  // 👈 mayor que tu versión anterior (2)
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    abstract fun comentarioDao(): ComentarioDao

    abstract fun compraDao(): CompraDao       // 👈 DAO de compras
}
