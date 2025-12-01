package com.example.proyectologin005d.viewmodel


// Importa el modelo de datos Post
import com.example.proyectologin005d.data.model.Post
// Importa los dispatchers de corrutinas
import kotlinx.coroutines.Dispatchers
// Importa la anotación para APIs experimentales de corrutinas
import kotlinx.coroutines.ExperimentalCoroutinesApi
// Importa el dispatcher de test sin confinamiento
import kotlinx.coroutines.test.UnconfinedTestDispatcher
// Importa función para resetear el dispatcher principal
import kotlinx.coroutines.test.resetMain
// Importa el builder para tests de corrutinas
import kotlinx.coroutines.test.runTest
// Importa función para establecer el dispatcher principal de test
import kotlinx.coroutines.test.setMain
// Importa anotación para método que se ejecuta después de cada test
import org.junit.jupiter.api.AfterEach
// Importa funciones de aserción para tests
import org.junit.jupiter.api.Assertions.*
// Importa anotación para método que se ejecuta antes de cada test
import org.junit.jupiter.api.BeforeEach
// Importa anotación para definir métodos de test
import org.junit.jupiter.api.Test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.ui.components.CarritoViewModel
import org.junit.Assert.assertEquals

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarritoViewModelTest {

    @Test
    fun addToCart_agrega_el_producto_y_actualiza_cantidad_y_total() {
        // Arrange
        val viewModel = CarritoViewModel()

        val producto = Producto(
            id = "PR001",
            nombre = "Manzana Fuji",
            precio = 1000,
            imagenUrl = null,
            categoria = "Frutas",
            stock = 5,
            imageRes = 0
        )

        // Ejecutamos en el hilo principal de Android
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            viewModel.addToCart(producto)
            viewModel.addToCart(producto)
        }

        // Act
        val cart = viewModel.cart.value
        val total = viewModel.getTotal()
        val cantidadEnCarrito = viewModel.countInCart("PR001")

        // Assert
        assertEquals(1, cart.size)
        assertEquals("PR001", cart[0].producto.id)
        assertEquals(2, cart[0].cantidad)
        assertEquals(2000, total)
        assertEquals(2, cantidadEnCarrito)
    }
}




// Anotación para indicar uso de APIs experimentales de corrutinas
@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    // Crea un dispatcher de test sin confinamiento para ejecución inmediata
    private val testDispatcher = UnconfinedTestDispatcher()
    // Método que se ejecuta antes de cada test
    @BeforeEach
    fun setUp() {
// Configurar el dispatcher de test antes de cada test
// Establece el dispatcher principal para tests
        Dispatchers.setMain(testDispatcher)
    }
    // Método que se ejecuta después de cada test
    @AfterEach
    fun tearDown() {
// Limpiar después de cada test
// Restaura el dispatcher principal original
        Dispatchers.resetMain()
    }
    // Test que verifica que postList contiene los datos esperados
    @Test
    fun `postList contiene datos esperados`() = runTest(testDispatcher) {
// Crea una lista falsa de posts para el test
        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Título 1", body = "Contenido 1"),
            Post(userId = 2, id = 2, title = "Título 2", body = "Contenido 2")
        )
// El ViewModel ahora usará el testDispatcher gracias a @BeforeEach
// Crea una instancia del ViewModel bajo test
        val viewModel = PostViewModel()
// Simulamos que fetchPosts() completó exitosamente
// Asigna directamente los datos falsos al StateFlow mutable
        viewModel._postList.value = fakePosts
// Verifica que el tamaño de la lista sea el esperado
        assertEquals(2, viewModel.postList.value.size)
// Verifica que el título del primer post sea correcto
        assertEquals("Título 1", viewModel.postList.value[0].title)
// Verifica que el cuerpo del segundo post sea correcto
        assertEquals("Contenido 2", viewModel.postList.value[1].body)
    }
    // Test básico de ejemplo sin lógica compleja
    @Test
    fun `test básico de ejemplo`() = runTest(testDispatcher) {
// Test simple que no depende del ViewModel
// Verifica una igualdad básica
        assertEquals(1, 1)
    }
}

