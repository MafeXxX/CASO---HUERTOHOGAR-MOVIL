package com.example.proyectologin005d.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectologin005d.data.model.Comentario
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioDao {

    @Insert
    suspend fun insertarComentario(comentario: Comentario)

    @Query("SELECT * FROM comentarios WHERE correo = :correo ORDER BY id DESC")
    fun obtenerComentarios(correo: String): Flow<List<Comentario>>
}
