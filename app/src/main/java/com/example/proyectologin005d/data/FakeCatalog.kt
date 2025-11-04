package com.example.proyectologin005d.data

import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.model.Producto

object FakeCatalog {
    val items = listOf(
        Producto(id = "VR001", nombre = "Lechuga Hidropónica", precio = 1200,
            imageRes = R.drawable.lechuga_hidroponica, stock = 20, categoria = "Verduras"),
        Producto(id = "FR001", nombre = "Manzana Fuji", precio = 1000,
            imageRes = R.drawable.manzana_fuji, stock = 35, categoria = "Frutas"),
        Producto(id = "FR002", nombre = "Palta Hass", precio = 2500,
            imageRes = R.drawable.palta_hass, stock = 15, categoria = "Frutas"),
        Producto(id = "GR001", nombre = "Quinoa Orgánica", precio = 3200,
            imageRes = R.drawable.quinoa_organica, stock = 10, categoria = "Granos"),
        Producto(id = "VR002", nombre = "Zanahoria", precio = 900,
            imageRes = R.drawable.zanahoria, stock = 40, categoria = "Verduras"),
    )
}
