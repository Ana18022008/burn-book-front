package com.example.burnbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.model.response.UsuarioResponse
import com.example.burnbook.repository.PublicacaoRepository
import com.example.burnbook.repository.UsuarioRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PerfilState {
    object Loading : PerfilState()
    data class Sucesso(
        val usuario: UsuarioResponse,
        val publicacoes: List<PublicacaoResponse> = emptyList(),
        val temMaisPaginas: Boolean = false  // <-- adicionado
    ) : PerfilState()
    data class Erro(val mensagem: String) : PerfilState()
}

class PerfilViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val publicacaoRepository: PublicacaoRepository
) : ViewModel() {

    private val _perfilState = MutableStateFlow<PerfilState>(PerfilState.Loading)
    val perfilState: StateFlow<PerfilState> = _perfilState

    private val publicacoes = mutableListOf<PublicacaoResponse>()
    private var paginaAtual = 0
    private var ultimaPagina = false

    fun carregarPerfil(usuarioId: Long) {
        viewModelScope.launch {
            _perfilState.value = PerfilState.Loading

            val usuarioDeferred     = async { usuarioRepository.buscarPorId(usuarioId) }
            val publicacoesDeferred = async { publicacaoRepository.listarPorUsuario(usuarioId, 0) }

            val usuarioResult     = usuarioDeferred.await()
            val publicacoesResult = publicacoesDeferred.await()

            if (usuarioResult.isFailure) {
                _perfilState.value = PerfilState.Erro(
                    usuarioResult.exceptionOrNull()?.message ?: "Erro ao carregar perfil"
                )
                return@launch
            }

            if (publicacoesResult.isSuccess) {
                val page = publicacoesResult.getOrNull()!!
                publicacoes.clear()
                publicacoes.addAll(page.content)
                ultimaPagina = page.last
                paginaAtual = 1
            }

            _perfilState.value = PerfilState.Sucesso(
                usuario       = usuarioResult.getOrNull()!!,
                publicacoes   = publicacoes.toList(),
                temMaisPaginas = !ultimaPagina  // <-- só true se existir próxima página
            )
        }
    }

    fun carregarPublicacoes(usuarioId: Long) {
        if (ultimaPagina) return
        viewModelScope.launch {
            val result = publicacaoRepository.listarPorUsuario(usuarioId, paginaAtual)
            if (result.isSuccess) {
                val page = result.getOrNull()!!
                publicacoes.addAll(page.content)
                ultimaPagina = page.last
                paginaAtual++

                val estadoAtual = _perfilState.value
                if (estadoAtual is PerfilState.Sucesso) {
                    _perfilState.value = estadoAtual.copy(
                        publicacoes    = publicacoes.toList(),
                        temMaisPaginas = !ultimaPagina
                    )
                }
            }
        }
    }

    fun recarregarPublicacoes(usuarioId: Long) {
        publicacoes.clear()
        paginaAtual = 0
        ultimaPagina = false
        carregarPerfil(usuarioId)
    }

}