package com.example.burnbook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.topBar
import com.example.burnbook.viewmodel.ComentarioState
import com.example.burnbook.viewmodel.ComentarioViewModel
import com.example.burnbook.viewmodel.PublicacaoViewModel

@Composable
fun PaginaComentarios(
    navController: NavController,
    viewModel: ComentarioViewModel,
    publicacaoViewModel: PublicacaoViewModel,
    publicacaoId: Long
) {
    var isDarkMode by remember { mutableStateOf(false) }


    val state by viewModel.comentariosState.collectAsState()
    val publicacaoBase by publicacaoViewModel.publicacaoSelecionada.collectAsState()

    var textoComentario by remember { mutableStateOf("") }


    LaunchedEffect(publicacaoId) {
        viewModel.carregarComentarios(publicacaoId)
        publicacaoViewModel.buscarPublicacao(publicacaoId)
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
                when (val s = state) {


                    is ComentarioState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFFF65B75)
                        )
                    }


                    is ComentarioState.Sucesso -> {
                        ListaDeComentarios(
                            isDarkMode = isDarkMode,
                            publicacao = publicacaoBase,
                            comentarios = (state as? ComentarioState.Sucesso)?.comentarios ?: emptyList()                        )
                    }


                    is ComentarioState.Erro -> {
                        Text(
                            text = s.mensagem,
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
            }

            BarraInputComentario(
                isDarkMode = isDarkMode,
                texto = textoComentario,
                onTextoChange = { textoComentario = it },
                onEnviar = {
                    if (textoComentario.isNotBlank()) {
                        viewModel.comentar(
                            ComentarioRequest(
                                publicacaoId = publicacaoId,
                                comentarioPaiId = null,
                                conteudo = textoComentario
                            ),
                            publicacaoId
                        )
                        textoComentario = ""
                    }
                }
            )
        }
    }
}

@Composable
fun ListaDeComentarios(isDarkMode: Boolean, publicacao: PublicacaoResponse?, comentarios: List<ComentarioResponse>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        publicacao?.let {
            item { PostPrincipalCard(isDarkMode, it) }
        }

        items(comentarios) { comentario ->
            ItemComentarioAPI(comentario, isDarkMode)
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
            }
        }
    }
}

@Composable
fun PostPrincipalCard(isDarkMode: Boolean, publicacao: PublicacaoResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) Color(0xFF606060) else Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = if (isDarkMode) Color.White else Color.Black
                )
                Spacer(Modifier.width(8.dp))
                publicacao.usernameAutor?.let {
                    Text(
                        it,
                        fontWeight = FontWeight.Medium,
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontFamily = inter,
                        fontSize = 15.sp
                    )
                }
            }
            Text(
                "11/10/2025",
                fontSize = 12.sp,
                color = if (isDarkMode) Color.White else Color.Black,
                fontFamily = cinzel,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = if (isDarkMode) Color.Gray else Color(0xFFEEEEEE)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF2F2F2),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    publicacao.conteudo,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkMode) Color.White else Color.Black,
                    fontFamily = inter
                )
            }
        }
    }
}


@Composable
fun BarraInputComentario(
    isDarkMode: Boolean,
    texto: String,
    onTextoChange: (String) -> Unit,
    onEnviar: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = if (isDarkMode) Color(0xFF2C2C2C) else Color.White,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (isDarkMode) Color(0xFF3C3C3C) else Color(0xFFF2F2F2),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (texto.isEmpty()) {
                    Text(
                        text = "Adicionar comentário... Seja gentil!",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = inter
                    )
                }
                androidx.compose.foundation.text.BasicTextField(
                    value = texto,
                    onValueChange = onTextoChange,
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
                onClick = onEnviar,
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
