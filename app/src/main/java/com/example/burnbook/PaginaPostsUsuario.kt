package com.example.burnbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//MODO CLARO E MODO ESCURO JÁ EDITADOS

@Composable
fun PaginaPostsUsuario (navController: NavController,
                        isDarkMode: Boolean,
                        onToggleDarkMode: () -> Unit ) {


    Scaffold (

        topBar = {
            topBar(
                isDarkMode = isDarkMode,
                onToggle = onToggleDarkMode
            )
        },

        bottomBar = {
            bottomBar(isDarkMode, navController)
        },

        ) {

            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6))
                .padding(innerPadding),
        ) {


            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){

                item {
                    titulo(isDarkMode)
                }

                items(3) {
                    cardSeusPosts(isDarkMode)
                }
            }

        }
    }
}

@Composable
fun titulo (isDarkMode: Boolean){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ){
        Text(
            text = "Seus Posts",
            fontFamily = Cinzel,
            fontSize = 34.sp,
            color = if (isDarkMode) Color.White else Color.Black
        )
    }

}
@Composable
fun cardSeusPosts(isDarkMode: Boolean) {

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
                        fontFamily = CinzelBold,
                        color = Color.Gray
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
                thickness = 1.dp,
                color = Color.LightGray
            )

            Text(
                text = "11/10/2025",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                fontSize = 14.sp,
                color = Color.Gray,
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
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ){

                IconButton(
                    onClick = {
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lixeira),
                        contentDescription = "Ícone de lixeira",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(18.dp)
                    )
                }
            }
        }
    }
}
