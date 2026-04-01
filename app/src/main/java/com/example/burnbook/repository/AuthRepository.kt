package com.example.burnbook.repository

import com.example.burnbook.api.AuthApi
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.model.request.CadastroRequest
import com.example.burnbook.model.request.LoginRequest
import com.example.burnbook.model.response.TokenResponse
import com.example.burnbook.model.response.UsuarioResponse
import com.example.burnbook.network.tratarResposta

class AuthRepository(private val api: AuthApi, private val dataStore: TokenDataStore) {

    suspend fun login(email: String, senha: String): Result<TokenResponse> {
        val result = tratarResposta { api.login(LoginRequest(email, senha)) }
        if (result.isSuccess) {
            dataStore.salvarToken(result.getOrNull()!!.token)
        }
        return result
    }

    suspend fun cadastrar(request: CadastroRequest): Result<UsuarioResponse> {
        return tratarResposta { api.cadastrar(request) }
    }

    suspend fun logout() {
        dataStore.limparToken()
    }


}
