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
                // Aqui chamamos o seu listarFeed
                val result = publicacaoRepository.listarFeed(paginaAtual)

                if (result.isSuccess) {
                    // ACESSO AO CONTEÚDO: Ajuste 'content' para o nome exato do campo no seu PageResponse
                    val pageResponse = result.getOrNull()
                    val novasPublicacoes = pageResponse?.content ?: emptyList()

                    // Verifica se o servidor avisou que é a última página
                    // (ajuste 'last' ou 'totalPages' conforme seu modelo PageResponse)
                    if (novasPublicacoes.isEmpty() || pageResponse?.last == true) {
                        todasCarregadas = true
                    }

                    if (novasPublicacoes.isNotEmpty()) {
                        val listaAtual = if (_uiState.value is FeedState.Sucesso) {
                            (_uiState.value as FeedState.Sucesso).publicacoes
                        } else {
                            emptyList()
                        }

                        // Filtro de segurança contra duplicados
                        val listaFiltrada = novasPublicacoes.filter { nova ->
                            listaAtual.none { atual -> atual.id == nova.id }
                        }

                        _uiState.value = FeedState.Sucesso(listaAtual + listaFiltrada)
                        paginaAtual++
                    }
                } else {
                    // Trata erro de 403 ou outros problemas de rede
                    if (_uiState.value !is FeedState.Sucesso) {
                        _uiState.value =
                            FeedState.Erro("Erro: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = FeedState.Erro(e.message ?: "Erro desconhecido")
            } finally {
                isCarregando = false
            }
        }
    }
    fun recarregar() {
        publicacoes.clear()
        paginaAtual = 0
        ultimaPagina = false
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
}
