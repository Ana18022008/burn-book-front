package com.example.burnbook.api

import com.example.burnbook.model.response.UsuarioResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioApi {
    @GET("api/usuarios/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): Response<UsuarioResponse>

    @GET("api/usuarios/username/{username}")
    suspend fun buscarPorUsername(@Path("username") username: String): Response<UsuarioResponse>
}
