package com.example.proyectologin005d.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectologin005d.data.model.CompraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {

    @Insert
    suspend fun insert(compra: CompraEntity): Long

    @Query("SELECT * FROM compra ORDER BY fechaMillis DESC")
    fun getAll(): Flow<List<CompraEntity>>
}
