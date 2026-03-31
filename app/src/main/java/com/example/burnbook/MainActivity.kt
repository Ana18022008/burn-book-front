package com.example.burnbook

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.burnbook.api.ComentarioApi
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.network.RetrofitInstance
import com.example.burnbook.repository.ComentarioRepository
import com.example.burnbook.ui.PaginaComentarios
import com.example.burnbook.ui.theme.BurnBookTheme
import com.example.burnbook.viewmodel.ComentarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            BurnBookTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                val tokenDataStore = remember { TokenDataStore(context) }

                NavHost(
                    navController = navController,
                    startDestination = "inicial"
                ) {


                    composable("comentarios") {
                        val context = LocalContext.current


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
                            publicacaoId = 1L
                        )
                    }

                    composable("inicial") { PaginaInicial(navController) }
                    composable("principal") { PaginaPrincipal(navController) }
                    composable("cadastro") { PaginaCadastro(navController) }
                    composable("login") { PaginaLogin(navController) }
                    composable("usuario") { PaginaUsuario(navController) }
                    composable("post") { PaginaCriacaoBlog(navController) }
                    composable("postsUser") { PaginaPostsUsuario(navController) }
                }
            }
        }
    }
}