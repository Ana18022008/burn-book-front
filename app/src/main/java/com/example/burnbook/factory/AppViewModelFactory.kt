package com.example.burnbook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.burnbook.repository.AuthRepository
import com.example.burnbook.repository.CategoriaRepository
import com.example.burnbook.repository.ComentarioRepository
import com.example.burnbook.repository.CurtidaRepository
import com.example.burnbook.repository.PublicacaoRepository
import com.example.burnbook.repository.UsuarioRepository
import com.example.burnbook.viewmodel.AuthViewModel
import com.example.burnbook.viewmodel.ComentarioViewModel
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PerfilViewModel
import com.example.burnbook.viewmodel.PublicacaoViewModel

class AppViewModelFactory(
    private val authRepository: AuthRepository,
    private val publicacaoRepository: PublicacaoRepository,
    private val curtidaRepository: CurtidaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val categoriaRepository: CategoriaRepository,
    private val comentarioRepository: ComentarioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authRepository) as T

            modelClass.isAssignableFrom(FeedViewModel::class.java) ->
                FeedViewModel(publicacaoRepository, curtidaRepository) as T

            modelClass.isAssignableFrom(PerfilViewModel::class.java) ->
                PerfilViewModel(usuarioRepository, publicacaoRepository) as T

            modelClass.isAssignableFrom(PublicacaoViewModel::class.java) ->
                PublicacaoViewModel(publicacaoRepository, categoriaRepository) as T

            modelClass.isAssignableFrom(ComentarioViewModel::class.java) ->
                ComentarioViewModel(comentarioRepository) as T

            else -> throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
        }
    }
}