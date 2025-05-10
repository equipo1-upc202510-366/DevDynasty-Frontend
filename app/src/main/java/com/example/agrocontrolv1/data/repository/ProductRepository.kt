package com.example.agrocontrolv1.data.repository

import com.example.agrocontrolv1.common.Resource
import com.example.agrocontrolv1.data.local.ProductDao
import com.example.agrocontrolv1.data.remote.ProductService
// No necesitas importar ProductDto aquí si solo mapeas Product a Product en el dominio (aunque es buena práctica)
// import com.example.agrocontrolv1.data.remote.dto.ProductDto
// import com.example.agrocontrolv1.data.remote.dto.toProduct
import com.example.agrocontrolv1.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException // Importa HttpException
import java.io.IOException // Importa IOException

class ProductRepository(private val service: ProductService, private val dao: ProductDao) {

    // El tipo de retorno Resource<List<Product>> está bien porque tu UI espera una lista
    suspend fun searchProduct(previousId: String): Resource<List<Product>> {
        // Ejecutamos la llamada de red en el Dispatcher.IO
        return withContext(Dispatchers.IO) {
            try {
                val response = service.searchProduct(previousId) // Esto devuelve Response<Product>

                if (response.isSuccessful) {
                    val product = response.body() // Obtenemos el objeto Product? del cuerpo

                    // Verificamos si el cuerpo no es nulo
                    if (product != null) {
                        // Si el cuerpo no es nulo, creamos una lista con ese único producto
                        Resource.Success(data = listOf(product))
                    } else {
                        // Si la respuesta fue isSuccessful pero el body es nulo (ej. 404 Not Found bien manejado por el server)
                        Resource.Error(message = "Producto con ID $previousId no encontrado.")
                    }
                } else {
                    // Si la respuesta NO fue isSuccessful (ej. 400, 500, etc.)
                    val errorBody = response.errorBody()?.string() // Intentamos leer el cuerpo del error
                    val errorCode = response.code() // Obtenemos el código HTTP

                    Resource.Error(message = "Error HTTP $errorCode: ${errorBody ?: response.message()}")
                    // Si errorBody es nulo, usamos el mensaje de la respuesta Retrofit.
                }
            } catch (e: IOException) {
                // Maneja errores de red (sin conexión, timeout, etc.)
                Resource.Error("Error de red. Verifica tu conexión o la dirección del servidor.")
            } catch (e: HttpException) {
                // Maneja otros posibles errores HTTP no capturados por response.isSuccessful (menos común aquí)
                Resource.Error("Error HTTP inesperado: ${e.localizedMessage}")
            } catch (e: Exception) {
                // Maneja cualquier otra excepción inesperada (ej. errores de parseo JSON)
                // El error de parseo JSON es muy probable que fuera la causa inicial.
                Resource.Error("Ocurrió un error inesperado: ${e.localizedMessage}")
            }
        }
    }
}