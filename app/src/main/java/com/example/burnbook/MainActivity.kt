package com.example.burnbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.burnbook.ui.theme.BurnBookTheme
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.burnbook.ui.PaginaComentarios
import com.example.burnbook.viewmodel.AuthViewModel
import com.example.burnbook.viewmodel.ComentarioViewModel
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PerfilViewModel
import com.example.burnbook.viewmodel.PublicacaoViewModel
import com.example.burnbook.api.ComentarioApi
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.network.RetrofitInstance
import com.example.burnbook.repository.ComentarioRepository
import com.example.burnbook.ui.PaginaComentarios
import com.example.burnbook.ui.theme.BurnBookTheme
import com.example.burnbook.viewmodel.ComentarioViewModel
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            BurnBookTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                val tokenDataStore = remember { TokenDataStore(context) }
                val token by tokenDataStore.getToken().collectAsState(initial = null)
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

                val authViewModel: AuthViewModel = viewModel(factory = factory)
                val feedViewModel: FeedViewModel = viewModel(factory = factory)
                val perfilViewModel: PerfilViewModel = viewModel(factory = factory)
                val comentarioViewModel: ComentarioViewModel = viewModel(factory = factory)
                val publicacaoViewModel: PublicacaoViewModel = viewModel(factory = factory)

                LaunchedEffect(token) {
                    if (token == null) {
                        navController.navigate("inicial") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "inicial") {
                    composable("inicial")   { PaginaInicial(navController) }
                    composable("login")     { PaginaLogin(navController, authViewModel) }
                    composable("cadastro")  { PaginaCadastro(navController, authViewModel) }
                    composable("principal") { PaginaPrincipal(navController, feedViewModel, publicacaoViewModel) }
                    composable("usuario")   { PaginaUsuario(navController, perfilViewModel, 153L) }
                    composable("postsUser") { PaginaPostsUsuario(navController, perfilViewModel, publicacaoViewModel, feedViewModel, 153L) }
                    composable("post")      { PaginaCriacaoBlog(navController, publicacaoViewModel, feedViewModel, perfilViewModel, 153L) }


                    composable("comentarios/{publicacaoId}") { backStackEntry ->
                        val publicacaoId = backStackEntry.arguments?.getString("publicacaoId")?.toLongOrNull() ?: return@composable
                        PaginaComentarios(navController, comentarioViewModel, publicacaoViewModel = publicacaoViewModel, publicacaoId)
                    }
      
        setContent {

            var isDarkMode by remember { mutableStateOf(false) }


            BurnBookTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val context = LocalContext.current

                NavHost(
                    navController = navController,
                    startDestination = "post"


                ) {
                    composable("comentarios") {
                        val tokenDataStore = remember { TokenDataStore(context) }
                        val retrofit = RetrofitInstance.create(tokenDataStore)
                        val api = retrofit.create(ComentarioApi::class.java)
                        val repository = ComentarioRepository(api)

                        val factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return ComentarioViewModel(repository) as T
                            }
                        }

                        val viewModel: ComentarioViewModel = viewModel(factory = factory)

                        PaginaComentarios(
                            navController = navController,
                            viewModel = viewModel,
                            publicacaoId = 1L,
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = { isDarkMode = !isDarkMode }
                        )
                    }
                    composable("inicial") { PaginaInicial(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("principal") { PaginaPrincipal(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("cadastro") { PaginaCadastro(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("login") { PaginaLogin(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("usuario") { PaginaUsuario(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("post") { PaginaCriacaoBlog(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }
                    composable("postsUser") { PaginaPostsUsuario(navController,isDarkMode = isDarkMode,onToggleDarkMode = { isDarkMode = !isDarkMode }) }

                }
            }
        }
    }
