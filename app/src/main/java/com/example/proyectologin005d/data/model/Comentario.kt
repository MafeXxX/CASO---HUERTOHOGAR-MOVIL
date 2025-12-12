package com.example.proyectologin005d.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comentarios")
data class Comentario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val correo: String,
    val texto: String
)
