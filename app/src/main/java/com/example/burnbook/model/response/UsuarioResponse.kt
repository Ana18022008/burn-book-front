package com.example.burnbook.model.response

data class UsuarioResponse(
    val id: Long,
    val username: String,
    val nome: String,
    val dataNascimento: String,
    val email: String,
    val fotoPerfilUrl: String?
)
