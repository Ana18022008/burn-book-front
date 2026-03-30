package com.example.burnbook.repository

import com.example.burnbook.api.CategoriaApi
import com.example.burnbook.model.response.CategoriaResponse
import com.example.burnbook.network.tratarResposta

class CategoriaRepository(private val api: CategoriaApi) {

    suspend fun listarTodas(): Result<List<CategoriaResponse>> {
        return tratarResposta { api.listarTodas() }
    }
}
