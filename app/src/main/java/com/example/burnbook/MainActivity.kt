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
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BurnBookTheme {
        Greeting("Android")
    }
}


