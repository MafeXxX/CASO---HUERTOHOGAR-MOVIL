package com.example.proyectologin005d

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectologin005d.data.SessionManager
import com.example.proyectologin005d.login.LoginScreen
import com.example.proyectologin005d.login.RegisterScreen
import com.example.proyectologin005d.ui.components.CarritoScreen
import com.example.proyectologin005d.ui.components.CarritoViewModel
import com.example.proyectologin005d.ui.components.ProductDetailScreen
import com.example.proyectologin005d.ui.components.PurchaseSuccessScreen
import com.example.proyectologin005d.ui.home.HomeScreen
import com.example.proyectologin005d.ui.profile.ProfileScreen
import com.example.proyectologin005d.ui.screens.CatalogScreen
import com.example.proyectologin005d.ui.splash.LoadingAppleScreen   // manzana girando
import com.example.proyectologin005d.ui.splash.LoadingPurchaseScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("ContextCastToActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val nav = rememberNavController()

                // ViewModel del carrito compartido en toda la app
                val carritoVM: CarritoViewModel =
                    viewModel(LocalContext.current as ComponentActivity)

                NavHost(navController = nav, startDestination = "login") {

                    // ===== LOGIN =====
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                // Al loguear, vamos a la pantalla de carga
                                nav.navigate("loading") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onGoToRegister = {
                                nav.navigate("register")
                            }
                        )
                    }

                    // ===== REGISTRO =====
                    composable("register") {
                        RegisterScreen(
                            onBackToLogin = {
                                nav.popBackStack()   // vuelve al login
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

                    // ===== HOME =====
                    composable("home") {
                        HomeScreen(
                            onGoToCatalog = { nav.navigate("catalog") },
                            onGoToProfile = { nav.navigate("profile") },
                            onGoToCart = { nav.navigate("carrito") },
                            onLogout = {
                                nav.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }

                    // ===== CATÁLOGO =====
                    composable("catalog") {
                        CatalogScreen(
                            onProductClick = { id ->
                                nav.navigate("detail/$id")
                            }
                        )
                    }

                    // ===== DETALLE DEL PRODUCTO =====
                    composable("detail/{id}") { back ->
                        val id = back.arguments?.getString("id")!!

                        ProductDetailScreen(
                            productId = id,
                            carritoViewModel = carritoVM,
                            onAddToCart = { carritoVM.addToCart(it) },
                            onBack = { nav.popBackStack() }
                        )
                    }

                    // ===== CARRITO =====
                    composable("carrito") {

                        // Obtenemos el email del usuario desde SessionManager
                        val context = LocalContext.current
                        val sessionManager = remember { SessionManager(context) }
                        val userEmail by sessionManager.emailFlow.collectAsState(initial = null)

                        CarritoScreen(
                            carritoViewModel = carritoVM,
                            userEmail = userEmail ?: "",   // 👈 se pasa al CarritoScreen
                            onBack = { nav.popBackStack() },
                            onBuyClick = {
                                nav.navigate("cargando")   // pantalla de carga
                            }
                        )
                    }

                    // ===== PANTALLA DE CARGA AL COMPRAR =====
                    composable("cargando") {
                        LoadingPurchaseScreen(
                            onFinished = {
                                nav.navigate("compraRealizada") {
                                    popUpTo("cargando") { inclusive = true }
                                }
                            }
                        )
                    }

                    // ===== APARTADO COMPRA REALIZADA =====
                    composable("compraRealizada") {

                        // Reutilizamos el mismo SessionManager y email
                        val context = LocalContext.current
                        val sessionManager = remember { SessionManager(context) }
                        val userEmail by sessionManager.emailFlow.collectAsState(initial = null)

                        PurchaseSuccessScreen(
                            onContinue = {
                                nav.navigate("home") {
                                    popUpTo("compraRealizada") { inclusive = true }
                                }
                            },
                            onBuy = {
                                // Si el usuario quiere comprar otra vez desde aquí,
                                // también registramos la compra en la BDD con su email
                                carritoVM.comprar(userEmail ?: "")
                            }
                        )
                    }

                    // ===== PERFIL =====
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
