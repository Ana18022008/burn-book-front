package com.example.burnbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.ui.inter
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PerfilState
import com.example.burnbook.viewmodel.PerfilViewModel
import com.example.burnbook.viewmodel.PublicacaoState
import com.example.burnbook.viewmodel.PublicacaoViewModel

@Composable
fun PaginaPostsUsuario(
    navController: NavController,
    perfilViewModel: PerfilViewModel,
    publicacaoViewModel: PublicacaoViewModel,
    feedViewModel: FeedViewModel,
    usuarioId: Long?
) {
    var isDarkMode by remember { mutableStateOf(false) }
    val perfilState by perfilViewModel.perfilState.collectAsState()
    val publicacaoState by publicacaoViewModel.uiState.collectAsState()

    LaunchedEffect(usuarioId) {
        usuarioId?.let { perfilViewModel.carregarPerfil(it) }
    }

    // Reage APENAS ao SucessoDelete — não confunde com criação
    LaunchedEffect(publicacaoState) {
        if (publicacaoState is PublicacaoState.SucessoDelete) {
            usuarioId?.let { perfilViewModel.recarregarPublicacoes(it) }


            feedViewModel.recarregar()

            publicacaoViewModel.resetarState()
        }
    }

    Scaffold(
        topBar = { topBar(isDarkMode = isDarkMode, onToggle = { isDarkMode = !isDarkMode }) },
        bottomBar = { bottomBar(isDarkMode, navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6))
                .padding(innerPadding)
        ) {
            when (val state = perfilState) {
                is PerfilState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFF65B75))
                    }
                }

                is PerfilState.Erro -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = state.mensagem,
                            color = Color(0xFFC0392B),
                            fontSize = 14.sp,
                            fontFamily = inter,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                }

                is PerfilState.Sucesso -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item { titulo(isDarkMode) }

                        if (state.publicacoes.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Você ainda não fez nenhuma publicação.",
                                        color = if (isDarkMode) Color.LightGray else Color(0xFF6D4C41),
                                        fontSize = 14.sp,
                                        fontFamily = inter
                                    )
                                }
                            }
                        } else {
                            items(state.publicacoes) { publicacao ->
                                cardSeusPosts(
                                    isDarkMode = isDarkMode,
                                    publicacao = publicacao,
                                    onDeletar = { publicacaoViewModel.deletar(publicacao.id) },
                                    onVerComentarios = {
                                        navController.navigate("comentarios/${publicacao.id}")
                                    }
                                )
                            }

                            if (state.temMaisPaginas) {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        TextButton(onClick = {
                                            usuarioId?.let { perfilViewModel.carregarPublicacoes(it) }
                                        }) {
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
                    }
                }
            }
        }
    }
}

@Composable
fun titulo(isDarkMode: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Seus Posts",
            fontFamily = Cinzel,
            fontSize = 34.sp,
            color = if (isDarkMode) Color.White else Color.Black
        )
    }
}

@Composable
fun cardSeusPosts(
    isDarkMode: Boolean,
    publicacao: PublicacaoResponse,
    onDeletar: () -> Unit,
    onVerComentarios: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.image_usuario),
                        contentDescription = "Ícone do usuário",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .height(25.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = if (publicacao.isAnonimo) "Anônimo" else (publicacao.usernameAutor ?: ""),
                        fontSize = 13.sp,
                        fontFamily = CinzelBold,
                        color = Color(0xFF2C2C2C)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icone_gostei),
                        contentDescription = "Curtidas",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(30.dp)
                    )
                    Text(
                        text = publicacao.quantidadeCurtidas.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(end = 8.dp),
                        fontFamily = CinzelBold
                    )
                    IconButton(
                        onClick = onVerComentarios,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icone_comentarios),
                            contentDescription = "Ver comentários",
                            tint = Color.Unspecified,
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )

            Text(
                text = publicacao.categoriaNome,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 14.sp,
                fontFamily = CinzelBold,
                color = Color(0xFFF65B75)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
                color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
            ) {
                Text(
                    text = publicacao.conteudo,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    color = if (isDarkMode) Color.White else Color(0xFF2C2C2C),
                    fontSize = 15.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDeletar) {
                    Icon(
                        painter = painterResource(id = R.drawable.lixeira),
                        contentDescription = "Deletar publicação",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(18.dp)
                    )
                }
            }
        }
    }
}