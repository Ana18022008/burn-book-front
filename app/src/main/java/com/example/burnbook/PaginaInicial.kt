package com.example.burnbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PaginaInicial(navController: NavController) {
    // 1. Box para o fundo rosa sólido e para a curva branca posterior
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF66BA)) // Rosa sólido
    ) {
        // 2. Column principal para alinhar os elementos de cima para baixo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espaço superior para a barra de status (se necessário)
            Spacer(modifier = Modifier.height(30.dp))

            // 3. Ícone da Lua no topo direito
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lua),
                    contentDescription = "Ícone de lua",
                    modifier = Modifier.size(50.dp)
                )
            }

            // 4. Espaço para o título
            Spacer(modifier = Modifier.height(180.dp))

            // 5. O Título "BURN BOOK" curvado (sua função corrigida)
            BurnBookTitle()

            // 6. Espaço flexível para empurrar o resto para baixo
            Spacer(modifier = Modifier.weight(1f))

            // 7. O Texto de Welcome e os outros textos (eles ficam sobre a parte branca)
            // ... (coloque o resto do seu código aqui) ...






//            Button(
//                onClick = {
//                    navController.navigate("principal")
//                }
//            ) {
//                Text("aaaaaaaa")
//            }




        }

    }
}

@Composable
fun BurnBookTitle() {
    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .width(330.dp)
            .height(360.dp)

    ) {
        // --- CONFIGURAÇÃO DO PINCEL (PAINT) ---
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 165f // Tamanho aproximado para parecer com a referência
            color = android.graphics.Color.WHITE // Cor do texto
            // Usamos serif NORMAL para "BURN"
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.SERIF, android.graphics.Typeface.NORMAL)
            textAlign = android.graphics.Paint.Align.CENTER // Centraliza o texto no caminho
        }

        val canvasWidth = size.width

        // --- 1. CONFIGURAÇÃO DO CAMINHO PARA O "BURN" (Arco Superior) ---
        // Para "curvar para cima", definimos o arco na metade inferior de um círculo imaginário
        // e começamos o texto no ângulo de 180° (esquerda).
        val pathBurn = android.graphics.Path().apply {
            // RectF(left, top, right, bottom)
            addArc(
                android.graphics.RectF(
                    -10f,          // Esquerda (ligeiramente fora para o arco ficar suave)
                    0f,            // Topo (onde o arco começa a curva)
                    canvasWidth / 2 + 100f, // Direita (curva na metade esquerda da tela)
                    250f           // Base (quão fundo a curva vai)
                ),
                180f, // Começa em 180° (esquerda)
                180f  // Gira 180° no sentido horário (até a direita)
            )
        }

        // --- 2. CONFIGURAÇÃO DO CAMINHO PARA O "BOOK" (Arco Inferior - Curvatura CONTRÁRIA) ---
        // O segredo para a curvatura oposta é o ângulo inicial em 0° e a varredura negativa!
        // E para posicionar corretamente, deslocamos o RectF para baixo e para a direita.
        val pathBook = android.graphics.Path().apply {
            addArc(
                android.graphics.RectF(
                    canvasWidth / 2 - 100f, // Esquerda (deslocado para o meio)
                    100f,                  // Topo (deslocado para baixo)
                    canvasWidth + 10f,       // Direita
                    280f                   // Base (quão fundo a curva vai)
                ),
                180f, // Começa em 180° (esquerda do seu próprio arco)
                -180f // Gira -180° no sentido ANTI-HORÁRIO (curvando para BAIXO)
            )
        }

        // --- 3. DESENHO DOS TEXTOS ---
        drawIntoCanvas { canvas ->
            // Desenha "BURN"
            canvas.nativeCanvas.drawTextOnPath("B U R N", pathBurn, 0f, 0f, paint)

            // Configura o pincel para Itálico e desenha "BOOK"
            paint.typeface = android.graphics.Typeface.create(android.graphics.Typeface.SERIF, android.graphics.Typeface.ITALIC)
            // vOffset = 30f ajuda a desgrudar um pouco mais se as letras encostarem
            canvas.nativeCanvas.drawTextOnPath("B O O K", pathBook, 0f, 30f, paint)
        }
    }
}