// app/src/main/java/com/example/proyectologin005d/navigation/AppNav.kt
package com.example.proyectologin005d.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ===== Pantallas (asegúrate de que existan en estas rutas) =====
import com.example.proyectologin005d.ui.login.LoginScreen
import com.example.proyectologin005d.ui.splash.LoadingAppleScreen

// Si ya tienes HomeScreen y NO un HomeMenuScreen, este alias evita renombrar archivos.
import com.example.proyectologin005d.ui.home.HomeScreen as HomeMenuScreen
// Si de verdad tienes HomeMenuScreen.kt, cambia la línea de arriba por:
// import com.example.proyectologin005d.ui.home.HomeMenuScreen

object Routes {
    const val LOGIN = "login"
    const val LOADING = "loading"
    const val HOME = "home"
}

@Composable
fun AppNav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.LOADING) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de manzana girando 6s
        composable(Routes.LOADING) {
            LoadingAppleScreen(
                onFinished = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOADING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeMenuScreen(
                onGoToCatalog = { /* navController.navigate("catalog") si la tienes */ },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onGoToProfile = { /* navController.navigate("profile") si la tienes */ }
            )
        }
    }
}
