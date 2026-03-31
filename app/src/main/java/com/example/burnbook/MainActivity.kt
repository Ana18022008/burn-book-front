package com.example.burnbook
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.burnbook.ui.theme.BurnBookTheme
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
                    composable("cadastro")  { PaginaCadastro(navController) }
                    composable("principal") { PaginaPrincipal(navController, feedViewModel) }
                    composable("usuario")   {
                        PaginaUsuario(navController, perfilViewModel, 153L)
                    }
                    composable("postsUser") { PaginaPostsUsuario(navController) }
                    composable("post")      { PaginaCriacaoBlog(navController) }
                    composable("comentarios") {
                        PaginaComentarios(navController, comentarioViewModel, 102L)
                    }
                }

            }

        }
    }
}