package com.example.burnbook.model.response

data class ErroResponse(
    val status: Int,
    val erro: String,
    val mensagem: String,
    val timestamp: String
)
