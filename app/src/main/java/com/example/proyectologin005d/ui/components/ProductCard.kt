package com.example.proyectologin005d.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun ProductCard(p: Producto, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        val imgRes = if (p.imageRes != 0) p.imageRes else R.drawable.logo_huerto

        Image(
            painter = painterResource(id = imgRes),
            contentDescription = p.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.padding(12.dp)) {
            Text(text = p.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = formatoCLP(p.precio), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun formatoCLP(n: Int) = "$" + "%,d".format(n).replace(',', '.')
