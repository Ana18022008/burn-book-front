package com.example.burnbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.repository.CurtidaRepository
import com.example.burnbook.repository.PublicacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FeedState {
    object Loading : FeedState()
    data class Sucesso(val publicacoes: List<PublicacaoResponse>) : FeedState()
    data class Erro(val mensagem: String) : FeedState()
}

class FeedViewModel(
    private val publicacaoRepository: PublicacaoRepository,
    private val curtidaRepository: CurtidaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedState>(FeedState.Loading)
    val uiState: StateFlow<FeedState> = _uiState

    private val publicacoes = mutableListOf<PublicacaoResponse>()
    private var paginaAtual = 0
    private var ultimaPagina = false

    fun carregarMais() {
        if (ultimaPagina) return
        viewModelScope.launch {
            val result = publicacaoRepository.listarFeed(paginaAtual)
            if (result.isSuccess) {
                val page = result.getOrNull()!!
                publicacoes.addAll(page.content)
                ultimaPagina = page.last
                paginaAtual++
                _uiState.value = FeedState.Sucesso(publicacoes.toList())
            } else {
                _uiState.value = FeedState.Erro(result.exceptionOrNull()?.message ?: "Erro ao carregar feed")
            }
        }
    }

    fun curtir(publicacaoId: Long) {
        viewModelScope.launch {
            val result = curtidaRepository.curtir(publicacaoId)
            if (result.isSuccess) {
                val curtida = result.getOrNull()!!
                val index = publicacoes.indexOfFirst { it.id == publicacaoId }
                if (index != -1) {
                    publicacoes[index] = publicacoes[index].copy(
                        curtidoPorMim = curtida.estadoCurtida,
                        quantidadeCurtidas = curtida.novaQuantidadeCurtidas
                    )
                    _uiState.value = FeedState.Sucesso(publicacoes.toList())
                }
            }
        }
    }
}
