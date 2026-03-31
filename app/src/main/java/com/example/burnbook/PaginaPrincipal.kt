package com.example.burnbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaPrincipal (navController: NavController) {

    var isDarkMode by remember { mutableStateOf(false) }

    Scaffold (

        // Barra Superior
        topBar = {
            topBar(
                isDarkMode = isDarkMode,
                onToggle = { isDarkMode = !isDarkMode }
            )
        },

        // Barra inferior
        bottomBar = {
            bottomBar(isDarkMode, navController)
        },

        ) {
        // Box principal
            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6)
                )
                .padding(innerPadding)
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp), // Espaço nas bordas
                verticalArrangement = Arrangement.spacedBy(30.dp) // Espaço entre os cards
            ){
                items(3) { cardPost(isDarkMode) }
            }

        }
    }
}


@Composable
fun topBar(isDarkMode: Boolean, onToggle: () -> Unit){
    val iconeTopo = if (isDarkMode) R.drawable.sol else R.drawable.lua_

    Surface (
        color = if (isDarkMode) Color(0xFFDE425C) else Color(0xFFF65B75),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)


    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp)
        ) {

            Column (
                verticalArrangement = Arrangement.spacedBy((-18).dp),
                modifier = Modifier.padding(top = 15.dp)
            ) {

                Text(
                    text = "Burn",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = JustMeFont
                )

                Text(
                    text = "BOOK",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontFamily = JaquesShadow
                )

            }
            IconButton(
                onClick = onToggle,
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = iconeTopo),
                    contentDescription = "Trocar modo",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = 7.dp)
                )
            }

        }
    }

}

@Composable
fun bottomBar(isDarkMode: Boolean, navController: NavController) {

    Surface (
        color = if (isDarkMode) Color(0xFFDE425C) else Color(0xFFF65B75),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ){

            IconButton(
                onClick = {
                    navController.navigate("principal")
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Ícone da casa/tela inicial",
                    tint = Color.Unspecified,
                    modifier = Modifier.height(50.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.postuser),
                contentDescription = "Ícone de posts do usuário",
                tint = Color.Unspecified,
                modifier = Modifier.height(30.dp)
            )
            IconButton(
                onClick = {
                    navController.navigate("post")
                },
            ) {
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
            IconButton(
                onClick = {
                    navController.navigate("usuario")
                },
            ) {
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
fun cardPost (isDarkMode: Boolean) {
    Surface (
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(315.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .fillMaxWidth()
            ) {

                Row {

                    Icon(
                        painter = painterResource(id = R.drawable.image_usuario),
                        contentDescription = "Ícone do usuário",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .height(25.dp)
                            .padding(end = 8.dp)
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.icone_gostei),
                        contentDescription = "Ícone de gostei/like",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(30.dp)
                    )

                    Text(
                        text = "01",
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
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                thickness = 1.dp, // Grossura da linha
                color = Color.LightGray
            )

            Text(
                text = "11/10/2025",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                fontSize = 14.sp,
                fontFamily = CinzelBold
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
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting " +
                            "industry. Lorem Ipsum has been the industry's standard dummy text ever " +
                            "since the 1500s, when an unknown printer took a galley of type and " +
                            "scrambled it to make a type specimen book. Lorem Ipsum is simply dummy text" +
                            " of the printing and typesetting industry.",
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
val inter = FontFamily(Font(R.font.inter))