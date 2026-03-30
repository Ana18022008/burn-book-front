package com.example.burnbook.api

import com.example.burnbook.model.request.LoginRequest
import com.example.burnbook.model.request.CadastroRequest
import com.example.burnbook.model.response.TokenResponse
import com.example.burnbook.model.response.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>

    @POST("api/usuarios")
    suspend fun cadastrar(@Body request: CadastroRequest): Response<UsuarioResponse>
}
