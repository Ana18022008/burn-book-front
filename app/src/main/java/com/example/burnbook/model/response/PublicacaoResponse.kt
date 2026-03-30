package com.example.burnbook.model.response

data class PublicacaoResponse(
    val id: Long,
    val usernameAutor: String?,
    val conteudo: String,
    val categoriaNome: String,
    val isAnonimo: Boolean,
    val quantidadeCurtidas: Int,
    val curtidoPorMim: Boolean
)
