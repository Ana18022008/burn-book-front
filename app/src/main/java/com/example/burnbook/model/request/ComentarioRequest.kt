package com.example.burnbook.model.request

data class ComentarioRequest(
    val publicacaoId: Long,
    val comentarioPaiId: Long?,
    val conteudo: String
)
