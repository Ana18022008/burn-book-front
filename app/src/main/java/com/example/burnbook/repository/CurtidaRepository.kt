package com.example.burnbook.repository

import com.example.burnbook.api.CurtidaApi
import com.example.burnbook.model.request.CurtidaRequest
import com.example.burnbook.model.response.CurtidaResponse
import com.example.burnbook.network.tratarResposta

class CurtidaRepository(private val api: CurtidaApi) {

    suspend fun curtir(publicacaoId: Long): Result<CurtidaResponse> {
        return tratarResposta { api.curtir(CurtidaRequest(publicacaoId)) }
    }
}
