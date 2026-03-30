package com.example.burnbook.network

import com.example.burnbook.model.response.ErroResponse
import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> tratarResposta(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null || response.code() == 204 || response.code() == 200) {
                @Suppress("UNCHECKED_CAST")
                Result.success(body as T)
            } else {
                Result.failure(Exception("Corpo da resposta vazio"))
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val mensagem = try {
                val erro = Gson().fromJson(errorBody, ErroResponse::class.java)
                erro.mensagem
            } catch (e: Exception) {
                "Erro desconhecido: ${response.code()}"
            }
            Result.failure(Exception(mensagem))
        }
    } catch (e: Exception) {
        Result.failure(Exception("Erro de conexão — verifique sua internet"))
    }
}
