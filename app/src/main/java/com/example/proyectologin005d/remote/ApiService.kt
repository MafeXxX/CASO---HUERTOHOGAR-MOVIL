package com.example.proyectologin005d.remote

import com.example.proyectologin005d.data.model.Post
import com.example.proyectologin005d.data.model.Producto
import retrofit2.http.GET
// Esta interfaz define los endpoints HTTP
interface ApiService {
    // Define una solicitud GET al endpoint /posts
    @GET(value = "/posts")
    suspend fun getPosts(): List<Post>


}