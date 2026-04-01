package com.example.burnbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.burnbook.model.response.PublicacaoResponse
import com.example.burnbook.viewmodel.FeedState
import com.example.burnbook.viewmodel.FeedViewModel
import com.example.burnbook.viewmodel.PublicacaoState
import com.example.burnbook.viewmodel.PublicacaoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaPrincipal(
    navController: NavController,
    feedViewModel: FeedViewModel,
    publicacaoViewModel: PublicacaoViewModel
) {
    var isDarkMode by remember { mutableStateOf(false) }

    val uiState by feedViewModel.uiState.collectAsState()
    val publicacaoState by publicacaoViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (uiState !is FeedState.Sucesso) {
            feedViewModel.carregarMais()
        }
    }

    LaunchedEffect(publicacaoState) {
        if (publicacaoState is PublicacaoState.SucessoDelete) {
            feedViewModel.recarregar()
        }
    }

    val deveCarregarMais by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItens = layoutInfo.totalItemsCount
            val ultimoVisivel = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            totalItens > 0 && ultimoVisivel >= (totalItens - 2)
        }
    }

    LaunchedEffect(deveCarregarMais) {
        if (deveCarregarMais) {
            feedViewModel.carregarMais()
        }
    }

    Scaffold(
        topBar = { topBar(isDarkMode, onToggle = { isDarkMode = !isDarkMode }) },
        bottomBar = { bottomBar(isDarkMode, navController) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6))
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is FeedState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFF65B75))
                    }
                }
                is FeedState.Erro -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.mensagem, color = Color.Red)
                    }
                }
                is FeedState.Sucesso -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        items(
                            items = state.publicacoes,
                            key = { it.id }
                        ) { publicacao ->
                            cardPost(
                                isDarkMode = isDarkMode,
                                publicacao = publicacao,
                                onCurtir = { feedViewModel.curtir(publicacao.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun topBar(isDarkMode: Boolean, onToggle: () -> Unit) {
    val iconeTopo = if (isDarkMode) R.drawable.sol else R.drawable.lua_

    Surface(
        color = if (isDarkMode) Color(0xFFDE425C) else Color(0xFFF65B75),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy((-18).dp)) {
                Text(text = "Burn", color = Color.White, fontSize = 30.sp, fontFamily = JustMeFont)
                Text(text = "BOOK", color = Color.White, fontSize = 25.sp, fontFamily = JaquesShadow)
            }
            IconButton(
                onClick = onToggle,
                modifier = Modifier.size(65.dp).padding(bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = iconeTopo),
                    contentDescription = "Trocar modo",
                    modifier = Modifier.size(40.dp).offset(y = 7.dp)
                )
            }
        }
    }
}

@Composable
fun bottomBar(isDarkMode: Boolean, navController: NavController) {
    Surface(
        color = if (isDarkMode) Color(0xFFDE425C) else Color(0xFFF65B75),
        modifier = Modifier.fillMaxWidth().height(64.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            IconButton(onClick = { navController.navigate("principal") }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Ícone da casa/tela inicial",
                    tint = Color.Unspecified,
                    modifier = Modifier.height(50.dp)
                )
            }
            Box(
                modifier = Modifier.size(40.dp).clickable { navController.navigate("postsUser") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.postuser),
                    contentDescription = "Ícone de posts do usuário",
                    tint = Color.Unspecified,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IconButton(onClick = { navController.navigate("post") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icone_adicao__2_),
                    contentDescription = "Ícone de adição de post",
                    tint = Color.Unspecified,
                    modifier = Modifier.height(50.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icone_comentarios),
                contentDescription = "Ícone de conversas",
                tint = Color.Unspecified,
                modifier = Modifier.height(30.dp)
            )
            IconButton(onClick = { navController.navigate("usuario") }) {
                Icon(
                    painter = painterResource(id = R.drawable.image_usuario),
                    contentDescription = "Ícone do usuário",
                    tint = Color.Unspecified,
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}

@Composable
fun cardPost(isDarkMode: Boolean, publicacao: PublicacaoResponse, onCurtir: () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth().height(315.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp).fillMaxWidth()
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.image_usuario),
                        contentDescription = "Ícone do usuário",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(25.dp).padding(end = 8.dp)
                    )
                    Text(
                        text = if (publicacao.isAnonimo) "Anônimo" else (publicacao.usernameAutor ?: ""),
                        fontSize = 14.sp,
                        fontFamily = CinzelBold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(onClick = onCurtir, modifier = Modifier.size(30.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.icone_gostei),
                            contentDescription = "Ícone de gostei/like",
                            tint = if (publicacao.curtidoPorMim) Color(0xFFF65B75) else Color.Unspecified,
                            modifier = Modifier.height(30.dp)
                        )
                    }
                    Text(
                        text = publicacao.quantidadeCurtidas.toString().padStart(2, '0'),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(end = 8.dp),
                        fontFamily = CinzelBold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icone_comentarios),
                        contentDescription = "Ícone de comentários",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(20.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .padding(end = 16.dp, start = 16.dp),
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
        }
    }
}

val JustMeFont = FontFamily(Font(R.font.justmeagaindownhere_regular))
val JaquesShadow = FontFamily(Font(R.font.jacquesfrancoisshadow_regular))
val Cinzel = FontFamily(Font(R.font.cinzel))
val CinzelBold = FontFamily(Font(R.font.cinzel_bold))