// app/src/main/java/com/example/proyectologin005d/navigation/AppNav.kt
package com.example.proyectologin005d.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ===== Pantallas =====
import com.example.proyectologin005d.login.LoginScreen
import com.example.proyectologin005d.login.RegisterScreen
import com.example.proyectologin005d.ui.splash.LoadingAppleScreen
import com.example.proyectologin005d.ui.home.HomeScreen as HomeMenuScreen
// Si de verdad tienes HomeMenuScreen.kt, cambia la línea de arriba por:
// import com.example.proyectologin005d.ui.home.HomeMenuScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val LOADING = "loading"
    const val HOME = "home"
    const val CART = "carrito"
}

@Composable
fun AppNav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        // LOGIN
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.LOADING) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        // REGISTER
        composable(Routes.REGISTER) {
            RegisterScreen(
                onBackToLogin = {
                    // Vuelve a la pantalla anterior (login)
                    navController.popBackStack()
                }
            )
        }

        // PANTALLA DE CARGA (MANZANA GIRANDO)
        composable(Routes.LOADING) {
            LoadingAppleScreen(
                onFinished = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOADING) { inclusive = true }
                    }
                }
            )
        }

        // HOME
        composable(Routes.HOME) {
            HomeMenuScreen(
                onGoToCatalog = { /* navController.navigate("catalog") si la tienes */ },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onGoToProfile = { /* navController.navigate("profile") si la tienes */ },
                onGoToCart = { navController.navigate(Routes.CART) }
            )
        }
    }
}
