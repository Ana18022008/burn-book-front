package com.example.burnbook.model.request

data class PublicacaoRequest(
    val conteudo: String,
    val categoriaId: Long,
    val isAnonimo: Boolean
)
