package com.example.burnbook.model.request

data class CadastroRequest(
    val username: String,
    val nome: String,
    val dataNascimento: String,
    val email: String,
    val senha: String,
    val cpf: String
)
