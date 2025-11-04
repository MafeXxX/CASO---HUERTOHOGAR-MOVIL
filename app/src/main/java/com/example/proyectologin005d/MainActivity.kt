package com.example.proyectologin005d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectologin005d.ui.home.HomeScreen
import com.example.proyectologin005d.ui.screens.CatalogScreen
import com.example.proyectologin005d.ui.login.LoginScreen
import com.example.proyectologin005d.ui.profile.ProfileScreen
import com.example.proyectologin005d.ui.splash.LoadingAppleScreen   // <-- NUEVO import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val nav = rememberNavController()

                NavHost(navController = nav, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                // Al loguear, vamos a la pantalla de carga
                                nav.navigate("loading") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // ===== Pantalla intermedia: manzana girando 6 s =====
                    composable("loading") {
                        LoadingAppleScreen(
                            onFinished = {
                                nav.navigate("home") {
                                    popUpTo("loading") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            onGoToCatalog = { nav.navigate("catalog") },
                            onGoToProfile = { nav.navigate("profile") },
                            onLogout = {
                                nav.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("catalog") {
                        CatalogScreen()
                    }

                    composable("profile") {
                        ProfileScreen(
                            onBack = { nav.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
