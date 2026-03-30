package com.example.burnbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burnbook.model.request.ComentarioRequest
import com.example.burnbook.model.response.ComentarioResponse
import com.example.burnbook.repository.ComentarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ComentarioState {
    object Loading : ComentarioState()
    data class Sucesso(val comentarios: List<ComentarioResponse>) : ComentarioState()
    data class Erro(val mensagem: String) : ComentarioState()
}

sealed class RespostaState {
    object Idle : RespostaState()
    object Loading : RespostaState()
    data class Sucesso(val respostas: List<ComentarioResponse>) : RespostaState()
    data class Erro(val mensagem: String) : RespostaState()
}

class ComentarioViewModel(
    private val repository: ComentarioRepository
) : ViewModel() {

    private val _comentariosState = MutableStateFlow<ComentarioState>(ComentarioState.Loading)
    val comentariosState: StateFlow<ComentarioState> = _comentariosState

    private val _respostasState = MutableStateFlow<RespostaState>(RespostaState.Idle)
    val respostasState: StateFlow<RespostaState> = _respostasState

    private val comentarios = mutableListOf<ComentarioResponse>()
    private var paginaAtual = 0
    private var ultimaPagina = false

    fun carregarComentarios(publicacaoId: Long) {
        if (ultimaPagina) return
        viewModelScope.launch {
            val result = repository.listarComentarios(publicacaoId, paginaAtual)
            if (result.isSuccess) {
                val page = result.getOrNull()!!
                comentarios.addAll(page.content)
                ultimaPagina = page.last
                paginaAtual++
                _comentariosState.value = ComentarioState.Sucesso(comentarios.toList())
            } else {
                _comentariosState.value = ComentarioState.Erro(result.exceptionOrNull()?.message ?: "Erro ao carregar comentários")
            }
        }
    }

    fun carregarRespostas(comentarioPaiId: Long) {
        viewModelScope.launch {
            _respostasState.value = RespostaState.Loading
            val result = repository.listarRespostas(comentarioPaiId)
            _respostasState.value = if (result.isSuccess) RespostaState.Sucesso(result.getOrNull()!!)
                                    else RespostaState.Erro(result.exceptionOrNull()?.message ?: "Erro ao carregar respostas")
        }
    }

    fun comentar(request: ComentarioRequest, publicacaoId: Long) {
        viewModelScope.launch {
            val result = repository.comentar(request)
            if (result.isSuccess) {
                comentarios.clear()
                paginaAtual = 0
                ultimaPagina = false
                carregarComentarios(publicacaoId)
            }
        }
    }

    fun deletar(id: Long, publicacaoId: Long) {
        viewModelScope.launch {
            val result = repository.deletar(id)
            if (result.isSuccess) {
                comentarios.removeIf { it.id == id }
                _comentariosState.value = ComentarioState.Sucesso(comentarios.toList())
            }
        }
    }
}
