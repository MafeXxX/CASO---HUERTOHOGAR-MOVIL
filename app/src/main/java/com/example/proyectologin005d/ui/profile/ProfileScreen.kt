package com.example.proyectologin005d.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.SessionManager
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val scroll = rememberScrollState()

    var email by remember { mutableStateOf("cliente@huerto.cl") }
    var username by remember { mutableStateOf("Usuario") }

    // Cargar datos guardados
    LaunchedEffect(Unit) {
        val savedEmail = session.getEmailOnce()
        if (!savedEmail.isNullOrBlank()) {
            email = savedEmail
            username = savedEmail.substringBefore("@").replaceFirstChar { it.uppercase() }
        }
    }

    // ===== IMAGEN DE PERFIL =====
    var avatarBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult
        avatarBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val src = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(src)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }


    //actualizacion de camara, implementacion para que funcione
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            avatarBitmap = bitmap
        } else {
            Toast.makeText(context, "No se pudo obtener la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    //codigo anterior permisos camara
    //val cameraLauncher = rememberLauncherForActivityResult(
    //    ActivityResultContracts.TakePicturePreview()
    //) {
    //    if (it != null) avatarBitmap = it
    //}
    // camara actualiza porque no se que hice preo ahora se cracheaba

    //actualizacion de permisos
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }






    // ===== UBICACIÓN =====
    var hasLocationPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val fine = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarse = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        hasLocationPermission = fine || coarse
    }

    // Pedir permisos al entrar
    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fine || coarse) {
            hasLocationPermission = true
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ------ ENCABEZADO ------
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text("Mi Perfil", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(20.dp))

        // ----- FOTO -----
        if (avatarBitmap != null) {
            Image(
                bitmap = avatarBitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(140.dp).clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.logo_huerto),
                contentDescription = null,
                modifier = Modifier.size(140.dp).clip(CircleShape)
            )
        }

        Spacer(Modifier.height(16.dp))

        // 👋 SALUDO PERSONALIZADO
        Text("Hola, $username.", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedButton({ galleryLauncher.launch("image/*") }) { Text("Elegir de galería") }
        Spacer(Modifier.height(8.dp))

        //boton anterior camara
        //OutlinedButton({ cameraLauncher.launch(null) }) { Text("Tomar foto") }

        //boton de prueba
        OutlinedButton({
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Tomar foto")
        }


        Spacer(Modifier.height(32.dp))

        // ----- CORREO -----
        Text("Correo", style = MaterialTheme.typography.titleMedium)
        Text(email, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(32.dp))

        // ----- UBICACIÓN -----
        Text("Mi ubicación actual", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        if (!hasLocationPermission) {
            Text(
                "Debes activar permisos de ubicación.",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LocationSection()
        }

        Spacer(Modifier.height(50.dp))
    }
}


@SuppressLint("MissingPermission")
@Composable
private fun LocationSection() {
    val context = LocalContext.current
    val locationManager =
        context.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager

    val prefs = remember {
        context.getSharedPreferences("profile_location_prefs", android.content.Context.MODE_PRIVATE)
    }

    var location by remember { mutableStateOf<Location?>(null) }
    var country by remember { mutableStateOf<String?>("-") }
    var region by remember { mutableStateOf<String?>("-") }
    var comuna by remember { mutableStateOf<String?>("-") }
    var calle by remember { mutableStateOf<String?>("-") }
    var error by remember { mutableStateOf<String?>(null) }

    // 👉 Para actualizar GPS cuando toque el botón:
    var reloadKey by remember { mutableStateOf(0) }

    // MAPA
    val viewport = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-70.6693, -33.4489))
            zoom(5.0)
        }
    }

    // OBTENER UBICACIÓN REAL AUTOMÁTICA Y CUANDO SE APRETA EL BOTÓN
    LaunchedEffect(reloadKey) {
        error = null

        suspendCancellableCoroutine<Location?> { cont ->
            val listener = object : LocationListener {
                override fun onLocationChanged(loc: Location) {
                    if (!cont.isCompleted) cont.resume(loc)
                    locationManager.removeUpdates(this)
                }
            }

            val provider = when {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                    LocationManager.GPS_PROVIDER
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                    LocationManager.NETWORK_PROVIDER
                else -> null
            }

            if (provider == null) {
                error = "GPS apagado"
                cont.resume(null)
                return@suspendCancellableCoroutine
            }

            locationManager.requestLocationUpdates(
                provider,
                0L,
                0f,
                listener,
                Looper.getMainLooper()
            )

            cont.invokeOnCancellation {
                locationManager.removeUpdates(listener)
            }
        }?.let { loc ->

            location = loc

            // Centrar mapa
            viewport.setCameraOptions {
                center(Point.fromLngLat(loc.longitude, loc.latitude))
                zoom(17.0)
            }

            // Guardar coordenadas
            prefs.edit()
                .putString("lat", loc.latitude.toString())
                .putString("lng", loc.longitude.toString())
                .apply()

            // Obtener dirección
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val results = withContext(Dispatchers.IO) {
                    geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                }

                if (!results.isNullOrEmpty()) {
                    val addr = results[0]
                    country = addr.countryName ?: "-"
                    region = addr.adminArea ?: "-"
                    comuna = addr.locality ?: addr.subAdminArea ?: "-"
                    calle = listOfNotNull(addr.thoroughfare, addr.subThoroughfare)
                        .joinToString(" ")

                    prefs.edit()
                        .putString("country", country)
                        .putString("region", region)
                        .putString("comuna", comuna)
                        .putString("calle", calle)
                        .apply()
                }
            } catch (e: Exception) {
                error = "Error obteniendo dirección"
            }
        }
    }

    // ---------------- UI -----------------

    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = viewport
        )

        // 🔴 Punto rojo que marca la ubicación
        if (location != null) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
        }
    }

    Spacer(Modifier.height(20.dp))

    Text("Dirección actual:", style = MaterialTheme.typography.titleMedium)

    Text("País: $country")
    Text("Región: $region")
    Text("Comuna: $comuna")
    Text("Calle: $calle")

    if (error != null) {
        Spacer(Modifier.height(10.dp))
        Text(error!!, color = MaterialTheme.colorScheme.error)
    }

    Spacer(Modifier.height(16.dp))

    // 🔁 BOTÓN PARA ACTUALIZAR UBICACIÓN
    Button(onClick = { reloadKey++ }) {
        Text("Actualizar ubicación")
    }

    Spacer(Modifier.height(20.dp))
}
