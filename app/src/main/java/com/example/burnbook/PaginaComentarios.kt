package com.example.burnbook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.burnbook.R
import com.example.burnbook.bottomBar
import com.example.burnbook.cinzel
import com.example.burnbook.model.request.ComentarioRequest
import com.example.burnbook.model.response.ComentarioResponse
import com.example.burnbook.topBar
import com.example.burnbook.viewmodel.ComentarioState
import com.example.burnbook.viewmodel.ComentarioViewModel

@Composable
fun PaginaComentarios(
    navController: NavController,
    viewModel: ComentarioViewModel,
    publicacaoId: Long
) {
    var isDarkMode by remember { mutableStateOf(false) }
    val comentariosState by viewModel.comentariosState.collectAsState()

    LaunchedEffect(publicacaoId) {
        viewModel.carregarComentarios(publicacaoId)
    }

    Scaffold(
        topBar = { topBar(isDarkMode, onToggle = { isDarkMode = !isDarkMode }) },
        bottomBar = { bottomBar(isDarkMode, navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFE6E6E6))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (val state = comentariosState) {
                    is ComentarioState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFFF65B75))
                        }
                    }
                    is ComentarioState.Erro -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = state.mensagem,
                                color = Color(0xFFC0392B),
                                fontSize = 14.sp,
                                fontFamily = inter,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    is ComentarioState.Sucesso -> {
                        ListaDeComentarios(
                            isDarkMode = isDarkMode,
                            comentarios = state.comentarios,
                            onCarregarMais = { viewModel.carregarComentarios(publicacaoId) }
                        )
                    }
                }
            }

            // Barra de input real conectada ao ViewModel
            BarraInputComentario(
                isDarkMode = isDarkMode,
                onEnviar = { conteudo ->
                    viewModel.comentar(
                        request = ComentarioRequest(
                            publicacaoId = publicacaoId,
                            comentarioPaiId = null,
                            conteudo = conteudo
                        ),
                        publicacaoId = publicacaoId
                    )
                }
            )
        }
    }
}

@Composable
fun ListaDeComentarios(
    isDarkMode: Boolean,
    comentarios: List<ComentarioResponse>,
    onCarregarMais: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(comentarios) { comentario ->
            ItemComentarioAPI(comentario, isDarkMode)
        }

        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextButton(onClick = onCarregarMais) {
                    Text(
                        text = "Carregar mais",
                        color = Color(0xFFF65B75),
                        fontFamily = inter
                    )
                }
            }
        }
    }
}

@Composable
fun ItemComentarioAPI(comentario: ComentarioResponse, isDarkMode: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 14.dp)
                .width(2.dp)
                .fillMaxHeight()
                .background(color = if (isDarkMode) Color(0xFF606060) else Color(0xFFCCCCCC))
        )

        Spacer(Modifier.width(12.dp))

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF606060) else Color.White
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isDarkMode) Color.LightGray else Color.Gray
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "@${comentario.autorNome}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontFamily = inter
                    )
                }

                Text(
                    text = comentario.dataHora,
                    fontSize = 10.sp,
                    color = if (isDarkMode) Color.LightGray else Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 2.dp),
                    fontFamily = cinzel
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.9.dp,
                    color = if (isDarkMode) Color.Gray else Color(0xFFEEEEEE)
                )

                Text(
                    text = comentario.conteudo,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = inter,
                    color = if (isDarkMode) Color.White else Color.Black
                )

                // Resumo do comentário pai — exibido quando é uma resposta
                comentario.comentarioPai?.let { pai ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = if (isDarkMode) Color(0xFF404040) else Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "↩ @${pai.autorNome}",
                                fontSize = 11.sp,
                                color = Color(0xFFF65B75),
                                fontFamily = inter,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = pai.conteudo,
                                fontSize = 11.sp,
                                color = if (isDarkMode) Color.LightGray else Color.Gray,
                                fontFamily = inter
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BarraInputComentario(
    isDarkMode: Boolean,
    onEnviar: (String) -> Unit
) {
    var texto by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = if (isDarkMode) Color(0xFF2C2C2C) else Color.White,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (isDarkMode) Color(0xFF404040) else Color(0xFFF2F2F2),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (texto.isEmpty()) {
                    Text(
                        text = "Adicione um comentário...",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = inter
                    )
                }
                BasicTextField(
                    value = texto,
                    onValueChange = { if (it.length <= 250) texto = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 13.sp,
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontFamily = inter
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (texto.isNotBlank()) {
                        onEnviar(texto)
                        texto = "" // limpa após envio
                    }
                },
                enabled = texto.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar comentário",
                    tint = if (texto.isNotBlank()) Color(0xFFF65B75) else Color.Gray
                )
            }
        }
    }
}
val inter = FontFamily(Font(R.font.inter))
