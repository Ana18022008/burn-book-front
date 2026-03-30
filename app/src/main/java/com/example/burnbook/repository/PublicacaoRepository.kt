package com.example.burnbook.repository

import com.example.burnbook.api.PublicacaoApi
import com.example.burnbook.model.request.PublicacaoRequest
import com.example.burnbook.model.response.PageResponse
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.network.tratarResposta

class PublicacaoRepository(private val api: PublicacaoApi) {

    suspend fun listarFeed(page: Int): Result<PageResponse<PublicacaoResponse>> {
        return tratarResposta { api.listarFeed(page) }
    }

    suspend fun listarPorUsuario(usuarioId: Long, page: Int): Result<PageResponse<PublicacaoResponse>> {
        return tratarResposta { api.listarPorUsuario(usuarioId, page) }
    }

    suspend fun buscarPorId(id: Long): Result<PublicacaoResponse> {
        return tratarResposta { api.buscarPorId(id) }
    }

    suspend fun criar(request: PublicacaoRequest): Result<PublicacaoResponse> {
        return tratarResposta { api.criar(request) }
    }

    suspend fun atualizar(id: Long, request: PublicacaoRequest): Result<PublicacaoResponse> {
        return tratarResposta { api.atualizar(id, request) }
    }

    suspend fun deletar(id: Long): Result<Unit> {
        return tratarResposta { api.deletar(id) }
    }
}
