package com.example.burnbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.burnbook.api.AuthApi
import com.example.burnbook.api.CategoriaApi
import com.example.burnbook.api.ComentarioApi
import com.example.burnbook.api.CurtidaApi
import com.example.burnbook.api.PublicacaoApi
import com.example.burnbook.api.UsuarioApi
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.factory.AppViewModelFactory
import com.example.burnbook.network.RetrofitInstance
import com.example.burnbook.repository.AuthRepository
import com.example.burnbook.repository.CategoriaRepository
import com.example.burnbook.repository.ComentarioRepository
import com.example.burnbook.repository.CurtidaRepository
import com.example.burnbook.repository.PublicacaoRepository
import com.example.burnbook.repository.UsuarioRepository
import com.example.burnbook.ui.theme.BurnBookTheme
import com.example.burnbook.viewmodel.AuthViewModel
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BurnBookTheme {
                val navController = rememberNavController()
                val tokenDataStore = remember { TokenDataStore(applicationContext) }
                val token by tokenDataStore.getToken().collectAsState(initial = null)

                LaunchedEffect(token) {
                    if (token == null) {
                        navController.navigate("inicial") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                val retrofit = remember { RetrofitInstance.create(tokenDataStore) }

                val factory = remember {
                    AppViewModelFactory(
                        authRepository       = AuthRepository(retrofit.create(AuthApi::class.java), tokenDataStore),
                        publicacaoRepository = PublicacaoRepository(retrofit.create(PublicacaoApi::class.java)),
                        curtidaRepository    = CurtidaRepository(retrofit.create(CurtidaApi::class.java)),
                        usuarioRepository    = UsuarioRepository(retrofit.create(UsuarioApi::class.java)),
                        categoriaRepository  = CategoriaRepository(retrofit.create(CategoriaApi::class.java)),
                        comentarioRepository = ComentarioRepository(retrofit.create(ComentarioApi::class.java))
                    )
                }

                val authViewModel: AuthViewModel     = viewModel(factory = factory)
                val feedViewModel: FeedViewModel     = viewModel(factory = factory)
                val perfilViewModel: PerfilViewModel = viewModel(factory = factory)

                NavHost(navController = navController, startDestination = "inicial") {
                    composable("inicial")   { PaginaInicial(navController) }
                    composable("login")     { PaginaLogin(navController, authViewModel) }
                    composable("cadastro")  { PaginaCadastro(navController) }
                    composable("principal") { PaginaPrincipal(navController, feedViewModel) }
                    composable("usuario")   {

                        PaginaUsuario(navController, perfilViewModel, 153L)
                    }
                    composable("postsUser") { PaginaPostsUsuario(navController) }
                    composable("post")      { PaginaCriacaoBlog(navController) }
                }
            }
        }
    }
}