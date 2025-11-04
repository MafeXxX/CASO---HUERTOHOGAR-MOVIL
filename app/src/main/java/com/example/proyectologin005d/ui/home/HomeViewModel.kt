package com.example.proyectologin005d.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.proyectologin005d.data.database.ProductoDataBase
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = true,
    val error: String? = null,
    val productos: List<Producto> = emptyList()
)

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val db by lazy {
        Room.databaseBuilder(app, ProductoDataBase::class.java, "hh.db").build()
    }
    private val repo by lazy { ProductoRepository(db.productoDao()) }

    private val _ui = MutableStateFlow(HomeUiState())
    val ui: StateFlow<HomeUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repo.seedIfNeeded(app)
                repo.productos().collectLatest { list ->
                    _ui.value = HomeUiState(loading = false, productos = list)
                }
            } catch (e: Exception) {
                _ui.value = HomeUiState(loading = false, error = e.message)
            }
        }
    }
}
