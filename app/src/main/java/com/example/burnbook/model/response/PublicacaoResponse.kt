package com.example.burnbook.model.response

import java.time.LocalDateTime

data class PublicacaoResponse(
    val id: Long,
    val usernameAutor: String?,
    val conteudo: String,
    val categoriaNome: String,
    val isAnonimo: Boolean,
    val quantidadeCurtidas: Int,
    val curtidoPorMim: Boolean,
    val dataHora: LocalDateTime
)
