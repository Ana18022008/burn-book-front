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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//MODO CLARO E MODO ESCURO JÁ EDITADOS

@Composable
fun PaginaUsuario(
    navController: NavController,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    Scaffold(

            topBar = {
                topBar(
                    isDarkMode = isDarkMode,
                    onToggle = onToggleDarkMode
                )
            },

            bottomBar = {
                bottomBarSimples(isDarkMode)
            },
        ) {

        innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            cardPerfil(isDarkMode, navController);
        }
    }
}



@Composable
fun cardPerfil (isDarkMode: Boolean, navController: NavController) {


    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6)
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.image_usuario
                    ),
                    contentDescription = "Foto do usuário",
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface (
                    modifier = Modifier
                        .width(380.dp)
                        .height(500.dp),
                    color =  if (isDarkMode) Color(0xFFC75A6A) else Color(0xFFF65B75).copy(alpha = 0.65f) ,
                    shape = RoundedCornerShape(20.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(35.dp)
                        ) {

                            Text(
                                text = "Ana Beatriz de Oliveira Ribeiro Silva Jr.",
                                fontFamily = Kadwa,
                                color = if (isDarkMode) Color.White else Color.Black,
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .padding(bottom = 30.dp, start = 12.dp, end = 12.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )


                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("NOME DE USUÁRIO", color = if (isDarkMode) Color.White else Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = cinzel)

                                Text(
                                    text = "teste_123",
                                    color = Color(0xFFF65B75),
                                    fontFamily = Kadwa,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6)),
                                    textAlign = TextAlign.Center
                                )
                            }


                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("EMAIL", color = if (isDarkMode) Color.White else Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = cinzel)

                                Text(
                                    text = "teste@gmail.com",
                                    color = Color(0xFFF65B75),
                                    fontSize = 24.sp,
                                    fontFamily = Kadwa,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6)),
                                    textAlign = TextAlign.Center
                                )
                            }


                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("DATA DE NASCIMENTO", color = if (isDarkMode) Color.White else Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = cinzel)

                                Text(
                                    text = "15/02/2008",
                                    color = Color(0xFFF65B75),
                                    fontSize = 24.sp,
                                    fontFamily = Kadwa,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isDarkMode) Color(0xFF161616) else Color(0xFFE6E6E6)),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { navController.navigate("principal") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkMode) Color(0xFFFF8EA1) else Color( 0xFFF65B75)
                    ),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text("Confirmar", fontSize = 20.sp, color = if (isDarkMode) Color.Black else Color.White)
                }

            }
        }
    }

}
val Kadwa = FontFamily(Font(R.font.kadwa))
val cinzel = FontFamily(Font(R.font.cinzel))

