package com.example.burnbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burnbook.model.request.PublicacaoRequest
import com.example.burnbook.model.response.CategoriaResponse
import com.example.burnbook.repository.CategoriaRepository
import com.example.burnbook.repository.PublicacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PublicacaoState {
    object Idle : PublicacaoState()
    object Loading : PublicacaoState()
    object SucessoCriacao : PublicacaoState()
    object SucessoDelete : PublicacaoState()
    data class Erro(val mensagem: String) : PublicacaoState()
}

class PublicacaoViewModel(
    private val publicacaoRepository: PublicacaoRepository,
    private val categoriaRepository: CategoriaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PublicacaoState>(PublicacaoState.Idle)
    val uiState: StateFlow<PublicacaoState> = _uiState

    private val _categorias = MutableStateFlow<List<CategoriaResponse>>(emptyList())
    val categorias: StateFlow<List<CategoriaResponse>> = _categorias

    init { carregarCategorias() }

    fun carregarCategorias() {
        viewModelScope.launch {
            val result = categoriaRepository.listarTodas()
            if (result.isSuccess) _categorias.value = result.getOrNull()!!
        }
    }

    fun resetarState() {
        _uiState.value = PublicacaoState.Idle
    }

    fun criar(request: PublicacaoRequest) {
        viewModelScope.launch {
            _uiState.value = PublicacaoState.Loading
            val result = publicacaoRepository.criar(request)
            _uiState.value = if (result.isSuccess) PublicacaoState.SucessoCriacao
            else PublicacaoState.Erro(result.exceptionOrNull()?.message ?: "Erro ao criar")
        }
    }

    fun atualizar(id: Long, request: PublicacaoRequest) {
        viewModelScope.launch {
            _uiState.value = PublicacaoState.Loading
            val result = publicacaoRepository.atualizar(id, request)
            _uiState.value = if (result.isSuccess) PublicacaoState.SucessoCriacao
            else PublicacaoState.Erro(result.exceptionOrNull()?.message ?: "Erro ao atualizar")
        }
    }

    fun deletar(id: Long) {
        viewModelScope.launch {
            _uiState.value = PublicacaoState.Loading
            val result = publicacaoRepository.deletar(id)
            _uiState.value = if (result.isSuccess) PublicacaoState.SucessoDelete
            else PublicacaoState.Erro(result.exceptionOrNull()?.message ?: "Erro ao deletar")
        }
    }
}