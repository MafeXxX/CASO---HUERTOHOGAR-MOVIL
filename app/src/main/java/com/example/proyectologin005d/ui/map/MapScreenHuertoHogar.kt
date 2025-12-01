package com.example.proyectologin005d.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

/**
 * Pantalla de mapa usando Mapbox.
 * La cámara se centra aprox. en Chile.
 */
@Composable
fun MapScreenHuertoHogar() {
    val viewportState = rememberMapViewportState {
        setCameraOptions {
            // Centro aprox en Chile
            center(Point.fromLngLat(-70.6693, -33.4489))
            zoom(4.5)
            pitch(0.0)
            bearing(0.0)
        }
    }

    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapViewportState = viewportState,
    )
}
