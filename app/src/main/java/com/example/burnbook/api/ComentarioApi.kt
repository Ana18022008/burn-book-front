package com.example.burnbook.api

import com.example.burnbook.model.request.ComentarioRequest
import com.example.burnbook.model.response.ComentarioResponse
import com.example.burnbook.model.response.PageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ComentarioApi {
    @GET("api/publicacoes/{publicacaoId}/comentarios")
    suspend fun listarComentarios(@Path("publicacaoId") publicacaoId: Long, @Query("page") page: Int, @Query("size") size: Int = 10): Response<PageResponse<ComentarioResponse>>

    @GET("api/comentarios/{comentarioPaiId}/respostas")
    suspend fun listarRespostas(@Path("comentarioPaiId") comentarioPaiId: Long): Response<List<ComentarioResponse>>

    @POST("api/comentarios")
    suspend fun comentar(@Body request: ComentarioRequest): Response<ComentarioResponse>

    @DELETE("api/comentarios/{id}")
    suspend fun deletar(@Path("id") id: Long): Response<Unit>
}
