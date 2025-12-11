package com.example.proyectologin005d.data.repository

import android.app.Application
import androidx.room.Room
import com.example.proyectologin005d.data.database.ProductoDataBase
import com.example.proyectologin005d.data.model.CompraEntity
import kotlinx.coroutines.flow.Flow

class CompraRepository(app: Application) {

    private val db = Room.databaseBuilder(
        app,
        ProductoDataBase::class.java,
        "producto_db"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val compraDao = db.compraDao()

    fun getCompras(): Flow<List<CompraEntity>> = compraDao.getAll()

    suspend fun registrarCompra(
        userEmail: String,
        total: Int,
        cantidadProductos: Int
    ) {
        val compra = CompraEntity(
            userEmail = userEmail,
            total = total,
            cantidadProductos = cantidadProductos,
            fechaMillis = System.currentTimeMillis()
        )
        compraDao.insert(compra)
    }
}
