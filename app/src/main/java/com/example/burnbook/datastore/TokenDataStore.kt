package com.example.burnbook.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth")

class TokenDataStore(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("jwt_token")

    suspend fun salvarToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun limparToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { it[TOKEN_KEY] }
    }

    private val USUARIO_ID_KEY = longPreferencesKey("usuario_id")

    suspend fun salvarUsuarioId(id: Long) {
        context.dataStore.edit { it[USUARIO_ID_KEY] = id }
    }

    fun getUsuarioId(): Flow<Long?> {
        return context.dataStore.data.map { it[USUARIO_ID_KEY] }
    }
}
