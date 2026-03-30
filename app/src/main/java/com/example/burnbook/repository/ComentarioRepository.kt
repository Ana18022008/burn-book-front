package com.example.burnbook.repository

import com.example.burnbook.api.ComentarioApi
import com.example.burnbook.model.request.ComentarioRequest
import com.example.burnbook.model.response.ComentarioResponse
import com.example.burnbook.model.response.PageResponse
import com.example.burnbook.network.tratarResposta

class ComentarioRepository(private val api: ComentarioApi) {

    suspend fun listarComentarios(publicacaoId: Long, page: Int): Result<PageResponse<ComentarioResponse>> {
        return tratarResposta { api.listarComentarios(publicacaoId, page) }
    }

    suspend fun listarRespostas(comentarioPaiId: Long): Result<List<ComentarioResponse>> {
        return tratarResposta { api.listarRespostas(comentarioPaiId) }
    }

    suspend fun comentar(request: ComentarioRequest): Result<ComentarioResponse> {
        return tratarResposta { api.comentar(request) }
    }

    suspend fun deletar(id: Long): Result<Unit> {
        return tratarResposta { api.deletar(id) }
    }
}
