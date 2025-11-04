package com.example.proyectologin005d.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectologin005d.data.dao.ProductoDao
import com.example.proyectologin005d.data.model.Producto

@Database(
    entities = [Producto::class],
    version = 1,
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}
