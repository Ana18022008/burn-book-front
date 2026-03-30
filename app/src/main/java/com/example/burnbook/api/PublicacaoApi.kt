package com.example.burnbook.api

import com.example.burnbook.model.request.PublicacaoRequest
import com.example.burnbook.model.response.PageResponse
import com.example.burnbook.model.response.PublicacaoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PublicacaoApi {
    @GET("api/feed")
    suspend fun listarFeed(@Query("page") page: Int, @Query("size") size: Int = 10): Response<PageResponse<PublicacaoResponse>>

    @GET("api/usuarios/{usuarioId}/publicacoes")
    suspend fun listarPorUsuario(@Path("usuarioId") usuarioId: Long, @Query("page") page: Int, @Query("size") size: Int = 10): Response<PageResponse<PublicacaoResponse>>

    @GET("api/publicacoes/{id}")
    suspend fun buscarPorId(@Path("id") id: Long): Response<PublicacaoResponse>

    @POST("api/publicacoes")
    suspend fun criar(@Body request: PublicacaoRequest): Response<PublicacaoResponse>

    @PUT("api/publicacoes/{id}")
    suspend fun atualizar(@Path("id") id: Long, @Body request: PublicacaoRequest): Response<PublicacaoResponse>

    @DELETE("api/publicacoes/{id}")
    suspend fun deletar(@Path("id") id: Long): Response<Unit>
}
