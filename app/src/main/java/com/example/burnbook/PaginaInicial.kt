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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController

val aksharFont = FontFamily(Font(R.font.akshar))
@Composable
fun PaginaInicial(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF66BA)) // Fundo rosa
    ) {

        // --- 1. DESENHO DA ONDA (EFEITO TOBOGÃ) ---
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val path = android.graphics.Path().apply {
                // Início: Um pouco acima do meio na lateral esquerda
                moveTo(0f, size.height * 0.45f)

                // cubicTo(control1X, control1Y, control2X, control2Y, endX, endY)
                cubicTo(
                    size.width * 0.3f, size.height * 0.45f, // Mantém o topo no início
                    size.width * 0.4f, size.height * 0.85f, // Desce rápido para criar a rampa
                    size.width, size.height * 0.75f         // Termina na direita mais embaixo
                )

                // Fecha o desenho para preencher o fundo de branco
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawIntoCanvas { canvas ->
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    style = android.graphics.Paint.Style.FILL
                    isAntiAlias = true
                }
                canvas.nativeCanvas.drawPath(path, paint)
            }
        }


        // --- 2. CONTEÚDO ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Lua
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Image(
                    painter = painterResource(id = R.drawable.lua),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(180.dp))

            // Título
            BurnBookTitle()

            Spacer(modifier = Modifier.weight(1f))

            // Seção de textos sobre o branco
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "WELCOME",
                    color = Color(0xFFFF66BA),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Segredos não são guardados...\nsão usados",
                    color = Color(0xFFC03582), // Rosa mais escuro para leitura no branco
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Botão Continue
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "continue",
                        color = Color(0xFFFF46AC),
                        fontSize = 22.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Se você tiver o ícone da seta no círculo, use aqui
                    Image(
                        painter = painterResource(id = R.drawable.seta), // Substitua pelo ícone da seta
                        modifier = Modifier.size(30.dp), // Rotação apenas se usar a lua como teste
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun BurnBookTitle() {
    val context = LocalContext.current
    val customTypeface = ResourcesCompat.getFont(context, R.font.kaisei_harunoumi)

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .width(330.dp)
            .height(300.dp)
    ) {
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 165f
            color = android.graphics.Color.WHITE
            typeface = customTypeface
            textAlign = android.graphics.Paint.Align.CENTER
        }

        val canvasWidth = size.width

        val pathBurn = android.graphics.Path().apply {
            addArc(
                android.graphics.RectF(-10f, 0f, canvasWidth / 2 + 100f, 300f),
                180f,
                180f
            )
        }

        val pathBook = android.graphics.Path().apply {
            addArc(
                android.graphics.RectF(canvasWidth / 2 - 100f, 120f, canvasWidth + 10f, 420f),
                180f,
                -180f
            )
        }

        drawIntoCanvas { canvas ->
            // --- DISTÂNCIA DO BURN ---
            // O último parâmetro (0f) é o vOffset.
            // Se colocar ex: 40f, ele desce o BURN em direção ao BOOK.
            canvas.nativeCanvas.drawTextOnPath("B U R N", pathBurn, 0f, 0f, paint)

            // --- DISTÂNCIA DO BOOK ---
            // O último parâmetro (30f) é o vOffset.
            // Diminuir para 10f ou usar valores negativos (ex: -20f)
            // faz o BOOK "subir" e grudar mais no BURN.
            canvas.nativeCanvas.drawTextOnPath("B O O K", pathBook, 0f, 20f, paint)
        }
    }
}

//modificar a font do regular e deixar medium (arquivos)