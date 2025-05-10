package com.example.agrocontrolv1.data.remote

// Ya no importes ResponseDto aquí para este método
// import com.example.agrocontrolv1.data.remote.dto.ResponseDto

import com.example.agrocontrolv1.domain.model.Product // Importa tu modelo de dominio Product
import retrofit2.Response // Sigue importando Response de Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products/{previousId}")
    // ¡Cambia el tipo de retorno para esperar directamente un objeto Product!
    suspend fun searchProduct(@Path("previousId") previousId: String): Response<Product>
}