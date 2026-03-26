package com.example.burnbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

@Composable
fun PaginaLogin(navController: NavController){

    var isDarkMode by remember {
        mutableStateOf(false)
    }



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
            bottomBarSimples(isDarkMode)
        },

        ) {
        // Box principal
            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkMode) Color(0xFF000000) else Color(0xFFE6E6E6)
                )
                .padding(innerPadding)
        ) {
            cardLogin(navController = navController, isDarkMode = isDarkMode)

        }
    }
}

@Composable
fun cardLogin(navController: NavController, isDarkMode: Boolean) {
    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color(0xFF000000) else Color(0xFFE6E6E6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(
                top = 115.dp,
                bottom = 0.dp
            ),
            text = "LOGIN",
            fontSize = 40.sp,
            fontFamily = fontCadastro,
            color = if (isDarkMode) Color(0xFFFF8EA1) else Color(0xFFF65B75)
        )

        Box(
            modifier = Modifier
                .width(347.dp)
                .height(306.dp)
                .background(
                    if (isDarkMode) Color(0x75F65B75) else Color(0x4AF65B75),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                Column(modifier = Modifier.width(319.dp)) {
                    Text(
                        text = "Nome de usuário:",
                        fontFamily = fontTopicos,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (isDarkMode) Color.White else Color.Black,
                        modifier = Modifier.padding(start = 5.dp, bottom = 10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = Color(0xF8FFFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Column(modifier = Modifier.width(319.dp)) {
                    Text(
                        text = "Senha:",
                        fontFamily = fontTopicos,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (isDarkMode) Color.White else Color.Black,
                        modifier = Modifier.padding(start = 5.dp, bottom = 10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = Color(0xF8FFFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = senha,
                            onValueChange = { senha = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Button(
                    onClick = { /* aplicar a lógica da nss api*/ },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(bottom = 17.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkMode) Color(0xFFFF8EA1) else Color(0xFFF65B75)
                    )
                ) {
                    Text(
                        text = "ENTRAR",
                        color = if (isDarkMode) Color.Black else Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontTopicos
                    )
                }

            }



        }

        Row(
            modifier = Modifier
                .padding(end = 45.dp, top = 8.dp)
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Não possui uma conta? ",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = if (isDarkMode) Color.White else Color.Black
            )
            TextButton(
                onClick = { navController.navigate("cadastro") },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(20.dp)
            ) {
                Text(
                    text = "cadastre-se",
                    color = if (isDarkMode) Color(0xFF2932E4) else Color(0xFF4750FF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}