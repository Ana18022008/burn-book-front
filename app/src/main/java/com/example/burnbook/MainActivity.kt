package com.example.burnbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.burnbook.api.*
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.factory.AppViewModelFactory
import com.example.burnbook.network.RetrofitInstance
import com.example.burnbook.repository.*
import com.example.burnbook.ui.PaginaComentarios
import com.example.burnbook.ui.theme.BurnBookTheme
import com.example.burnbook.viewmodel.*

class MainActivity : ComponentActivity() {

    private lateinit var tokenDataStore: TokenDataStore
    private lateinit var authViewModel: AuthViewModel
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var perfilViewModel: PerfilViewModel
    private lateinit var comentarioViewModel: ComentarioViewModel
    private lateinit var publicacaoViewModel: PublicacaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        tokenDataStore = TokenDataStore(applicationContext)
        val retrofit = RetrofitInstance.create(tokenDataStore)

        val factory = AppViewModelFactory(
            authRepository       = AuthRepository(retrofit.create(AuthApi::class.java), tokenDataStore),
            publicacaoRepository = PublicacaoRepository(retrofit.create(PublicacaoApi::class.java)),
            curtidaRepository    = CurtidaRepository(retrofit.create(CurtidaApi::class.java)),
            usuarioRepository    = UsuarioRepository(retrofit.create(UsuarioApi::class.java)),
            categoriaRepository  = CategoriaRepository(retrofit.create(CategoriaApi::class.java)),
            comentarioRepository = ComentarioRepository(retrofit.create(ComentarioApi::class.java))
        )

        authViewModel      = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        feedViewModel      = ViewModelProvider(this, factory)[FeedViewModel::class.java]
        perfilViewModel    = ViewModelProvider(this, factory)[PerfilViewModel::class.java]
        comentarioViewModel = ViewModelProvider(this, factory)[ComentarioViewModel::class.java]
        publicacaoViewModel = ViewModelProvider(this, factory)[PublicacaoViewModel::class.java]

        setContent {
            BurnBookTheme {
                val navController = rememberNavController()

                val token by tokenDataStore.getToken().collectAsState(initial = "carregando")
                val usuarioId by tokenDataStore.getToken().collectAsState(initial = null)

                LaunchedEffect(token) {
                    if (token == null) {
                        navController.navigate("inicial") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "inicial") {
                    composable("inicial")    { PaginaInicial(navController) }
                    composable("login")      { PaginaLogin(navController, authViewModel) }
                    composable("cadastro")   { PaginaCadastro(navController) }
                    composable("principal")  { PaginaPrincipal(navController, feedViewModel, publicacaoViewModel) }
                    composable("post")       { PaginaCriacaoBlog(navController, publicacaoViewModel, feedViewModel, perfilViewModel, 153L) }
                    composable("usuario") {

                        PaginaUsuario(navController, perfilViewModel, 153L)
                    }
                    composable("postsUser") {
                        PaginaPostsUsuario(navController, perfilViewModel, publicacaoViewModel, 153L)
                    }
                    composable("comentarios/{publicacaoId}") { backStackEntry ->
                        val publicacaoId = backStackEntry.arguments?.getString("publicacaoId")?.toLongOrNull() ?: return@composable
                        PaginaComentarios(navController, comentarioViewModel, publicacaoId)
                    }
                }
            }
        }
    }
}