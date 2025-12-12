package com.example.proyectologin005d.data.repository

import android.content.Context
import androidx.room.Room
import com.example.proyectologin005d.data.database.ProductoDataBase
import com.example.proyectologin005d.data.model.Comentario
import kotlinx.coroutines.flow.Flow

class ComentarioRepository(context: Context) {

    // ⚠️ Usa siempre el mismo nombre que tu otra DB si ya la usabas.
    // Si no recuerdas, "producto_db" está bien para desarrollo.
    private val db: ProductoDataBase = Room.databaseBuilder(
        context.applicationContext,
        ProductoDataBase::class.java,
        "producto_db"
    )
        .fallbackToDestructiveMigration()   // opcional pero útil mientras desarrollas
        .build()

    private val dao = db.comentarioDao()

    suspend fun guardarComentario(correo: String, texto: String) {
        dao.insertarComentario(
            Comentario(
                correo = correo,
                texto = texto
            )
        )
    }

    fun obtenerComentarios(correo: String): Flow<List<Comentario>> {
        return dao.obtenerComentarios(correo)
    }
}
