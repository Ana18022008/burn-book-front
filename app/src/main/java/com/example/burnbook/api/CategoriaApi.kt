package com.example.burnbook.api

import com.example.burnbook.model.response.CategoriaResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoriaApi {
    @GET("api/categorias")
    suspend fun listarTodas(): Response<List<CategoriaResponse>>
}
