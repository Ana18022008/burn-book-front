package com.example.burnbook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.example.burnbook.Kadwa
import com.example.burnbook.R
import com.example.burnbook.bottomBar
import com.example.burnbook.cinzel
import com.example.burnbook.inter
import com.example.burnbook.viewmodel.ComentarioViewModel
import com.example.burnbook.viewmodel.ComentarioState
import com.example.burnbook.model.response.ComentarioResponse
import com.example.burnbook.topBar


@Composable
fun PaginaComentarios(
    navController: NavController,
    viewModel: ComentarioViewModel,
    publicacaoId: Long
) {
    var isDarkMode by remember { mutableStateOf(false) }

    val comentariosFake = listOf(
        ComentarioResponse(1, "Ana_Silva", null, "Nossa, eu também estou assim hoje! 😫", "12/10/2025 09:30"),
        ComentarioResponse(2, "Joao_Dev", null, "Força, o final de semana está chegando!", "12/10/2025 10:15"),
        ComentarioResponse(3, "Burner_01", null, "Essa empresa acaba com a gente kkk", "12/10/2025 11:00"),
        ComentarioResponse(4, "Café_Lovers", null, "Um café resolve tudo (ou quase tudo).", "12/10/2025 11:20")
    )

    Scaffold(
        topBar = { topBar(isDarkMode, onToggle = { isDarkMode = !isDarkMode }) },
        bottomBar = { bottomBar(isDarkMode, navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFE6E6E6))
                .padding(innerPadding)
        ) {

            ListaDeComentarios(
                isDarkMode = isDarkMode,
                comentarios = comentariosFake
            )
        }
    }
}

@Composable
fun ListaDeComentarios(isDarkMode: Boolean, comentarios: List<ComentarioResponse>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item { PostPrincipalCard(isDarkMode) }


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
fun PostPrincipalCard(isDarkMode: Boolean) {
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
                Text("@Emanuelle_Hostin", fontWeight = FontWeight.Medium, color = if (isDarkMode) Color.White else Color.Black, fontFamily = inter, fontSize = 15.sp)
            }
            Text("11/10/2025", fontSize = 12.sp, color = if (isDarkMode) Color.White else Color.Black, fontFamily = cinzel, fontWeight = FontWeight.SemiBold)

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
                    "QUE DIA CANSATIVO\nNÃO QUERO MAIS TRABALHAR\n#EXAUSTA",
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkMode) Color.White else Color.Black,
                    fontFamily = inter
                )
            }
        }
    }
}
