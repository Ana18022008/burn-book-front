package com.example.burnbook

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//MODO CLARO E MODO ESCURO JÁ EDITADOS

@Composable
fun PaginaCriacaoBlog (navController: NavController,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6))
                .padding(innerPadding),

        ) {
            criarPost(isDarkMode, navController)

        }
    }
}

@Composable
fun criarPost(isDarkMode: Boolean, navController: NavController) {

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

            Text("CONTEÚDO", fontFamily = cinzel, fontWeight = FontWeight.Bold, color = Color.Black)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Lorem Ipsum",
                    modifier = Modifier.padding(8.dp),
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text("CATEGORIA", fontFamily = cinzel, fontWeight = FontWeight.Bold, color = Color.Black)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Selecione a categoria da publicação",
                    modifier = Modifier.padding(8.dp),
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text("ANÔNIMO", fontFamily = cinzel, fontWeight = FontWeight.Bold, color = Color.Black)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                color = if (isDarkMode) Color(0xFF505050) else Color(0xFFE6E6E6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Selecione se a publicação será anônima",
                    modifier = Modifier.padding(8.dp),
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate("principal") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF65B75)
                    )
                ) {
                    Text("Publicar", color = Color.White)
                }
            }
        }
    }
}