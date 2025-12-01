package com.example.proyectologin005d.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.repository.PostRepository
import com.example.proyectologin005d.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel que mantiene el estado de los datos obtenidos
class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    internal val _postList = MutableStateFlow<List<Post>>(emptyList())
    val postList: StateFlow<List<Post>> = _postList

    init {
        viewModelScope.launch {
            fetchPosts()
        }
    }

    suspend fun fetchPosts() {
        try {
            _postList.value = repository.getPosts()
        } catch (e: Exception) {
            println("Error al obtener datos: ${e.localizedMessage}")
        }
    }
}
