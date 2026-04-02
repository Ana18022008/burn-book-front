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

    private var isCarregando = false
    private var todasCarregadas = false

    fun carregarMais() {
        if (isCarregando || todasCarregadas) return

        isCarregando = true
        viewModelScope.launch {
            try {
                val result = publicacaoRepository.listarFeed(paginaAtual)

                if (result.isSuccess) {
                    val pageResponse = result.getOrNull()
                    val novasPublicacoes = pageResponse?.content ?: emptyList()

                    if (novasPublicacoes.isEmpty() || pageResponse?.last == true) {
                        todasCarregadas = true
                    }

                    if (novasPublicacoes.isNotEmpty()) {
                        val listaFiltrada = novasPublicacoes.filter { nova ->
                            publicacoes.none { atual -> atual.id == nova.id }
                        }

                        publicacoes.addAll(listaFiltrada)

                        _uiState.value = FeedState.Sucesso(publicacoes.toList())
                        paginaAtual++
                    } else if (paginaAtual == 0) {
                        _uiState.value = FeedState.Sucesso(emptyList())
                    }
                }
            } finally {
                isCarregando = false
            }
        }
    }
    fun recarregar() {
        publicacoes.clear()
        paginaAtual = 0
        todasCarregadas = false
        ultimaPagina = false
        _uiState.value = FeedState.Loading
        carregarMais()
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
    fun removerPostLocal(id: Long) {
        val removido = publicacoes.removeAll { it.id == id }

        if (removido) {
            _uiState.value = FeedState.Sucesso(publicacoes.toList())
        }
    }
}
