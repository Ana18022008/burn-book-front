package com.example.burnbook
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.burnbook.ui.theme.BurnBookTheme
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.burnbook.api.AuthApi
import com.example.burnbook.datastore.TokenDataStore
import com.example.burnbook.factory.AuthViewModelFactory
import com.example.burnbook.network.RetrofitInstance
import com.example.burnbook.repository.AuthRepository
import com.example.burnbook.viewmodel.AuthViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BurnBookTheme {
                val navController = rememberNavController()
                val tokenDataStore = remember { TokenDataStore(applicationContext) }
                val token by tokenDataStore.getToken().collectAsState(initial = null)

                // Redireciona para login automaticamente quando token for nulo
                LaunchedEffect(token) {
                    if (token == null) {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                // Instancia o ViewModel compartilhado entre as telas de auth
                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(
                        AuthRepository(
                            RetrofitInstance.create(tokenDataStore).create(AuthApi::class.java),
                            tokenDataStore
                        )
                    )
                )

                NavHost(navController = navController, startDestination = "inicial") {
                    composable("inicial") { PaginaInicial(navController) }
                    composable("principal") { PaginaPrincipal(navController) }
                    composable("cadastro") { PaginaCadastro(navController) }
                    composable("login") { PaginaLogin(navController, authViewModel) }
                    composable("usuario") { PaginaUsuario(navController) }
                }
            }
        }
    }
}