package com.example.burnbook
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.burnbook.ui.theme.BurnBookTheme
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BurnBookTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "principal"
                ) {

                    composable("inicial") {
                        PaginaInicial(navController)
                    }

                    composable("principal") {
                        PaginaPrincipal(navController)
                    }

                    composable("cadastro"){
                        PaginaCadastro(navController)
                    }

                    composable("login"){
                        PaginaLogin(navController)
                    }

                    composable("usuario") {
                        PaginaUsuario(navController)
                    }

                    composable("post"){
                        PaginaCriacaoBlog(navController)
                    }

                    composable("postsUser") {
                        PaginaPostsUsuario(navController)
                    }


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


