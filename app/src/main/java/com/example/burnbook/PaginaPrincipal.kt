package com.example.burnbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaPrincipal () {
    Scaffold (

        // Barra Superior
        topBar = {
            topBarFun()
        },

        // Barra inferior
        bottomBar = {
            bottomBarFun()
        },

    ) {
        // Box principal
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Conteúdo da página...

    }
    }
}



@Composable
fun topBarFun() {
    Surface (
        color = Color(0xFFFF66BA),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.icone_burnbook),
                contentDescription = "Ícone do Burn Book",
                tint = Color.Unspecified,
                modifier = Modifier.height(40.dp)
            )

            // Arrumar a fonte
            Text(text = "Burn Book",
                color = Color.White,
                fontSize = 20.sp
                )

            Icon(painter = painterResource(id = R.drawable.image_usuario),
                contentDescription = "Foto de perfil do usuário",
                tint = Color.Unspecified,
                modifier = Modifier.height(30.dp)
            )

        }
    }

}
@Composable
fun bottomBarFun() {


}
