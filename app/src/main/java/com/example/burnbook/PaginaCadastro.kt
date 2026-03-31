package com.example.burnbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//MODO CLARO E MODO ESCURO JÁ EDITADOS

@Composable
fun PaginaCadastro(
    navController: NavController,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit ){


    Scaffold (

        topBar = {
            topBar(
                isDarkMode = isDarkMode,
                onToggle = onToggleDarkMode
            )
        },

        bottomBar = {
            bottomBarSimples(isDarkMode)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)

        ) {

            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkMode) Color(0xFF000000) else Color(0xFFE6E6E6)
                )
                .padding(innerPadding)
        ) {
            cardCadastro(navController = navController, isDarkMode = isDarkMode)

        }
    }
}


@Composable
fun bottomBarSimples(isDarkMode: Boolean){
    Surface (
        color = if (isDarkMode) Color(0xFFDE425C) else Color(0xFFF65B75),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)

    ) {

    }
}

@Composable
fun cardCadastro(navController: NavController,isDarkMode: Boolean) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNascimento by remember {mutableStateOf("")}
    var user by remember {mutableStateOf("")}
    var senha by remember {mutableStateOf("")}

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkMode) Color(0xFF111111) else Color(0xFFE6E6E6)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
                text = "CADASTRO",
                fontSize = 40.sp,
                fontFamily = fontCadastro,
                color = if (isDarkMode) Color(0xFFFF8EA1) else Color(0xFFF65B75)
            )

            Box(
                modifier = Modifier
                    .width(347.dp)
                    .height(562.dp)
                    .background(
                        if (isDarkMode) Color(0x75F65B75) else Color(0x4AF65B75),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {


                    Column(modifier = Modifier.width(319.dp)) {
                        Text(
                            text = "Nome:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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
                            text = "Email:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Column(modifier = Modifier.width(319.dp)) {
                        Text(
                            text = "CPF:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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
                                value = cpf,
                                onValueChange = { cpf = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Column(modifier = Modifier.width(319.dp)) {
                        Text(
                            text = "Data de nascimento:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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
                                value = dataNascimento,
                                onValueChange = { dataNascimento = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Column(modifier = Modifier.width(319.dp)) {
                        Text(
                            text = "Nome de usuário:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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
                                value = user,
                                onValueChange = { user = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Column(modifier = Modifier.width(319.dp)) {
                        Text(
                            text = "Senha:",
                            fontFamily = fontTopicos,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
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

                }



            }


            Spacer(modifier = Modifier.height(17.dp))


            Button(
                onClick = { /* aplicar a lógica da nss api*/ },
                modifier = Modifier
                    .width(200.dp)
                    .padding(bottom = 17.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                colors = buttonColors(
                    containerColor = if (isDarkMode) Color(0xFFFF8EA1) else Color(0xFFF65B75)
                )
            ) {
                Text(
                    text = "CRIAR CONTA",
                    color = if (isDarkMode) Color.Black else Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontTopicos
                )
            }

            Row(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Já possui uma conta? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = if (isDarkMode) Color.White else Color.Black
                )
                TextButton(
                    onClick = { navController.navigate("login") },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    modifier = Modifier.height(20.dp)
                ) {
                    Text(
                        text = "faça login",
                        color = if (isDarkMode) Color(0xFF2932E4) else Color(0xFF4750FF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }





        }
    }
}


val fontCadastro = FontFamily(Font(R.font.kadwa_regular))
val fontTopicos = FontFamily(Font(R.font.kaisei_harunoumi))