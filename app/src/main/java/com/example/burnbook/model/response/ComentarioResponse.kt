package com.example.burnbook.model.response

data class ComentarioResponse(
    val id: Long,
    val autorNome: String,
    val comentarioPai: ComentarioPaiResumo?,
    val conteudo: String,
    val dataHora: String,
    val publicacaoId:Long
)

data class ComentarioPaiResumo(
    val autorNome: String,
    val conteudo: String,
    val dataHora: String
)
