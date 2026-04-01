package com.example.burnbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.burnbook.model.request.PublicacaoRequest
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PerfilViewModel
import com.example.burnbook.viewmodel.PublicacaoState
import com.example.burnbook.viewmodel.PublicacaoViewModel

@Composable
fun PaginaCriacaoBlog(
    navController: NavController,
    publicacaoViewModel: PublicacaoViewModel,
    feedViewModel: FeedViewModel,
    perfilViewModel: PerfilViewModel,
    usuarioId: Long
) {
    var isDarkMode by remember { mutableStateOf(false) }
    val uiState by publicacaoViewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is PublicacaoState.SucessoCriacao) {
            feedViewModel.recarregar()
            perfilViewModel.recarregarPublicacoes(usuarioId)
            publicacaoViewModel.resetarState()

            navController.navigate("principal") {
                popUpTo("principal") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(Unit) {
        publicacaoViewModel.carregarCategorias()
    }

    Scaffold(
        topBar = {
            topBar(isDarkMode = isDarkMode, onToggle = { isDarkMode = !isDarkMode })
        },
        bottomBar = {
            bottomBar(isDarkMode, navController)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6))
                .padding(innerPadding),
        ) {
            criarPost(
                isDarkMode = isDarkMode,
                publicacaoViewModel = publicacaoViewModel,
                uiState = uiState
            )
        }
    }
}

@Composable
fun criarPost(
    isDarkMode: Boolean,
    publicacaoViewModel: PublicacaoViewModel,
    uiState: PublicacaoState
) {
    var conteudo by remember { mutableStateOf("") }
    var isAnonimo by remember { mutableStateOf(false) }
    var categoriaExpandida by remember { mutableStateOf(false) }
    var categoriaSelecionadaId by remember { mutableStateOf<Long?>(null) }
    var categoriaSelecionadaNome by remember { mutableStateOf("Selecione a categoria") }

    val categorias by publicacaoViewModel.categorias.collectAsState()

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            Text("CONTEÚDO", fontFamily = cinzel, fontWeight = FontWeight.Bold)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                shape = RoundedCornerShape(8.dp)
            ) {
                BasicTextField(
                    value = conteudo,
                    onValueChange = { conteudo = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontSize = 15.sp
                    ),
                    decorationBox = { inner ->
                        if (conteudo.isEmpty()) {
                            Text("Escreva sua publicação...", color = Color.Gray, fontSize = 15.sp)
                        }
                        inner()
                    }
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text("CATEGORIA", fontFamily = cinzel, fontWeight = FontWeight.Bold)

            Box {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { categoriaExpandida = true }
                ) {
                    Box(contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = categoriaSelecionadaNome,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            color = if (isDarkMode) Color.White else Color.Black
                        )
                    }
                }

                DropdownMenu(
                    expanded = categoriaExpandida,
                    onDismissRequest = { categoriaExpandida = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nome) },
                            onClick = {
                                categoriaSelecionadaId = categoria.id
                                categoriaSelecionadaNome = categoria.nome
                                categoriaExpandida = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text("ANÔNIMO", fontFamily = cinzel, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isAnonimo) "Sim, publicar anonimamente" else "Não, usar meu nome",
                    color = if (isDarkMode) Color.White else Color.Black,
                    fontSize = 14.sp
                )
                Switch(
                    checked = isAnonimo,
                    onCheckedChange = { isAnonimo = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFF65B75)
                    )
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            if (uiState is PublicacaoState.Erro) {
                Text(
                    text = (uiState as PublicacaoState.Erro).mensagem,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val categoriaId = categoriaSelecionadaId ?: return@Button
                        if (conteudo.isBlank()) return@Button
                        publicacaoViewModel.criar(
                            PublicacaoRequest(
                                conteudo = conteudo,
                                categoriaId = categoriaId,
                                isAnonimo = isAnonimo
                            )
                        )
                    },
                    enabled = uiState !is PublicacaoState.Loading
                            && conteudo.isNotBlank()
                            && categoriaSelecionadaId != null,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF65B75),
                        disabledContainerColor = Color(0xFFF65B75).copy(alpha = 0.5f)
                    )
                ) {
                    if (uiState is PublicacaoState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Publicar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}