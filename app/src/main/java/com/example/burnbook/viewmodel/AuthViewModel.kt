package com.example.burnbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burnbook.model.request.CadastroRequest
import com.example.burnbook.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Sucesso : AuthState()
    data class Erro(val mensagem: String) : AuthState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val result = repository.login(email, senha)
            _uiState.value = if (result.isSuccess) AuthState.Sucesso
                             else AuthState.Erro(result.exceptionOrNull()?.message ?: "Erro no login")
        }
    }

    fun cadastrar(request: CadastroRequest) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val result = repository.cadastrar(request)
            _uiState.value = if (result.isSuccess) AuthState.Sucesso
                             else AuthState.Erro(result.exceptionOrNull()?.message ?: "Erro no cadastro")
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.value = AuthState.Idle
        }
    }
}
