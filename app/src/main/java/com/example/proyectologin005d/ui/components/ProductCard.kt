package com.example.proyectologin005d.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.model.Producto

@Composable
private fun ProductCard(
    p: Producto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        val imgRes = if (p.imageRes != 0) p.imageRes else R.drawable.logo_huerto

        Image(
            painter = painterResource(id = imgRes),
            contentDescription = p.nombre,
            modifier = Modifier
                .fillMaxSize()
                .height(140.dp),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.padding(12.dp)) {
            Text(p.nombre, style = MaterialTheme.typography.titleMedium)
            Text(formatoCLP(p.precio), style = MaterialTheme.typography.bodyMedium)
        }
    }
}


//comentario, solo funciona aqui el formatoCLP, si se borra lo demas igual funciona
fun formatoCLP(n: Int) = "$" + "%,d".format(n).replace(',', '.')
