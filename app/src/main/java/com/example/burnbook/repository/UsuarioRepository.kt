package com.example.burnbook.repository

import com.example.burnbook.api.UsuarioApi
import com.example.burnbook.model.response.UsuarioResponse
import com.example.burnbook.network.tratarResposta

class UsuarioRepository(private val api: UsuarioApi) {

    suspend fun buscarPorId(id: Long): Result<UsuarioResponse> {
        return tratarResposta { api.buscarPorId(id) }
    }

    suspend fun buscarPorUsername(username: String): Result<UsuarioResponse> {
        return tratarResposta { api.buscarPorUsername(username) }
    }
}
