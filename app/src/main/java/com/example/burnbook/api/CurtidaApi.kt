package com.example.burnbook.api

import com.example.burnbook.model.request.CurtidaRequest
import com.example.burnbook.model.response.CurtidaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CurtidaApi {
    @POST("api/curtidas")
    suspend fun curtir(@Body request: CurtidaRequest): Response<CurtidaResponse>
}
