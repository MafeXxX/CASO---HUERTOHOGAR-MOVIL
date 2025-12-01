package com.example.proyectologin005d.repository


// Importa la clase Post del modelo de datos
import com.example.proyectologin005d.data.model.Post
// Importa la interfaz ApiService para llamadas a la API
// Importa el repositorio que se va a testear
import com.example.proyectologin005d.data.repository.PostRepository
// Importa el estilo StringSpec de Kotest para escribir tests
import io.kotest.core.spec.style.StringSpec
//import io.kotest.core.spec.style.scopes.StringSpecRootScope.invoke
// Importa el matcher para verificar que una colección contiene exactamente los elementos esperados
import io.kotest.matchers.collections.shouldContainExactly
// Importa coEvery de MockK para definir comportamiento de funciones suspendidas
import io.mockk.coEvery
// Importa mockk para crear objetos mock
import io.mockk.mockk
// Importa runTest para ejecutar tests con corrutinas
import kotlinx.coroutines.test.runTest

import com.example.proyectologin005d.data.dao.ProductoDao
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.data.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake DAO para pruebas (solo en memoria)
class FakeProductoDao : ProductoDao {

    private val productosInternos = mutableListOf<Producto>()

    override fun getAll(): Flow<List<Producto>> {
        return flowOf(productosInternos.toList())
    }

    override suspend fun count(): Int = productosInternos.size

    override suspend fun insertAll(list: List<Producto>) {
        productosInternos.clear()
        productosInternos.addAll(list)
    }
}

class ProductoRepositoryTest {

    @Test
    fun productos_devuelve_el_producto_insertado() = runBlocking {
        // Arrange
        val fakeDao = FakeProductoDao()
        val repo = ProductoRepository(fakeDao)

        val productoEsperado = Producto(
            id = "PR001",
            nombre = "Manzana Fuji",
            precio = 1000,
            imagenUrl = null,
            categoria = "Frutas",
            stock = 10,
            imageRes = 0
        )

        fakeDao.insertAll(listOf(productoEsperado))

        // Act
        val listaResultado = repo.productos().first()

        // Assert
        assertEquals(1, listaResultado.size)
        assertEquals("PR001", listaResultado[0].id)
        assertEquals("Manzana Fuji", listaResultado[0].nombre)
        assertEquals(1000, listaResultado[0].precio)
    }
}


class PostRepositoryTest : StringSpec({
// Define un test case con descripción en lenguaje natural
    "getPosts() debe retornar una lista de posts simulada huerta-" {
// Crea una lista falsa de posts para usar en el test
        val fakePosts = listOf(
// Crea el primer post de prueba con datos simulados
            Post(userId = 1, id = 1, title = "Título 1", body = "Cuerpo 1"),
// Crea el segundo post de prueba con datos simulados
            Post(userId = 2, id = 2, title = "Título 2", body = "Cuerpo 2")
        )
// Mock del PostRepository completo
// Crea un objeto mock de PostRepository usando MockK
        val repo = mockk<PostRepository>()
// Define el comportamiento del mock: cuando se llame a getPosts(), retornar fakePosts
        coEvery { repo.getPosts() } returns fakePosts
// Ejecuta el test en un contexto de corrutinas
        runTest {
// Llama al método getPosts() del repositorio mockeado
            val result = repo.getPosts()
// Verifica que el resultado contenga exactamente los mismos posts que fakePosts
            result shouldContainExactly fakePosts
        }
    }
})

