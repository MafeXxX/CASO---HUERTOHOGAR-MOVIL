package com.example.proyectologin005d.data.repository

import android.content.Context
import com.example.proyectologin005d.data.dao.ProductoDao
import com.example.proyectologin005d.data.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray

class ProductoRepository(private val dao: ProductoDao) {

    fun productos(): Flow<List<Producto>> = dao.getAll()

    suspend fun seedIfNeeded(context: Context) = withContext(Dispatchers.IO) {
        if (dao.count() > 0) return@withContext
        val json = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val arr = JSONArray(json)
        val list = mutableListOf<Producto>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list += Producto(
                id = o.getString("id"),
                nombre = o.getString("nombre"),
                precio = o.getInt("precio"),
                imagenUrl = o.optString("imagenUrl", null),
                categoria = o.optString("categoria", null),
                stock = o.optInt("stock", 0)
            )
        }
        dao.insertAll(list)
    }
}
