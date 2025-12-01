package com.example.proyectologin005d

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                //algo nuevo añadido del carrito
                val carritoVM: CarritoViewModel = viewModel(LocalContext.current as ComponentActivity)

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
                    //catalogo modificado
                    composable("catalog") {
                        CatalogScreen(
                            onProductClick = { id ->
                                nav.navigate("detail/$id")
                            }
                        )
                    }
                    // detalle del producto
                    composable("detail/{id}") { back ->
                        val id = back.arguments?.getString("id")!!

                        ProductDetailScreen(
                            productId = id,
                            carritoViewModel = carritoVM,
                            onAddToCart = { carritoVM.addToCart(it) },
                            onBack = { nav.popBackStack() }
                        )
                    }
                    // carrito
                    composable("carrito") {
                        CarritoScreen(
                            carritoViewModel = carritoVM,
                            onBack = { nav.popBackStack() },
                            onBuyClick = { nav.navigate("cargando") }   // <-- AQUÍ NAVEGA A LA PANTALLA DE CARGA
                        )
                    }
                    // pantalla de carga al realizar la accion de comprar
                    composable("cargando") {
                        LoadingPurchaseScreen(
                            onFinished = {
                                nav.navigate("compraRealizada") {
                                    popUpTo("cargando") { inclusive = true }
                                }
                            }
                        )
                    }
                    // apartado compra realizada
                    composable("compraRealizada") {

                        val carritoViewModel: CarritoViewModel = viewModel()

                        PurchaseSuccessScreen(
                            onContinue = {
                                nav.navigate("home") {
                                    popUpTo("compraRealizada") { inclusive = true }
                                }
                            },
                            onBuy = {
                                carritoViewModel.comprar()   // <-- AQUÍ LLAMAS A LA FUNCIÓN COMPRAR
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
