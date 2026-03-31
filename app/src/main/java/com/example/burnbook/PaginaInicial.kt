package com.example.burnbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

val aksharFont = FontFamily(Font(R.font.akshar))

@Composable
fun PaginaInicial(navController: NavController) {
    var isDarkMode by remember { mutableStateOf(false) }

    val corDaOndaETitulo = if (isDarkMode) Color(0xFF0D0D0D) else Color.White
    val iconeTopo = if (isDarkMode) R.drawable.sol else R.drawable.lua

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F65B75))
    ) {

        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val path = android.graphics.Path().apply {
                moveTo(0f, size.height * 0.45f)
                cubicTo(
                    size.width * 0.3f, size.height * 0.45f,
                    size.width * 0.4f, size.height * 0.85f,
                    size.width, size.height * 0.75f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawIntoCanvas { canvas ->
                val paint = android.graphics.Paint().apply {
                    color = corDaOndaETitulo.toArgb()
                    style = android.graphics.Paint.Style.FILL
                    isAntiAlias = true
                }
                canvas.nativeCanvas.drawPath(path, paint)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { isDarkMode = !isDarkMode },
                    modifier = Modifier.size(50.dp)) {
                    Image(
                        painter = painterResource(id = iconeTopo),
                        contentDescription = "Trocar modo",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(180.dp))


            BurnBookTitle(corTexto = corDaOndaETitulo)


            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "WELCOME",
                    color = Color(0xFFFF667F),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Light,
                        fontSize = 30.sp
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Segredos não são guardados...\nsão usados",
                    fontFamily = aksharFont,
                    color = if (isDarkMode) Color(0xFFEDC6CE) else Color(0xFFC4213C),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "continue",
                        color = if (isDarkMode) Color(0xFFFF667F) else Color(0xFFF65B75),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))


                    IconButton(
                        onClick = {
                            navController.navigate("login")
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.seta),
                            modifier = Modifier.size(30.dp),
                            contentDescription = null,
                            colorFilter = if (isDarkMode) ColorFilter.tint(Color(0xFFFF667F)) else ColorFilter.tint(Color(0xFFF65B75)),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BurnBookTitle(corTexto: Color) {
    val context = LocalContext.current
    val customTypeface = ResourcesCompat.getFont(context, R.font.kaisei_harunoumi)

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .width(330.dp)
            .height(180.dp)
    ) {
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 165f
            color = corTexto.toArgb()
            typeface = customTypeface
            textAlign = android.graphics.Paint.Align.CENTER
        }

        val canvasWidth = size.width
        val pathBurn = android.graphics.Path().apply {
            addArc(android.graphics.RectF(-10f, 0f, canvasWidth / 2 + 100f, 300f), 180f, 180f)
        }
        val pathBook = android.graphics.Path().apply {
            addArc(android.graphics.RectF(canvasWidth / 2 - 100f, 120f, canvasWidth + 10f, 420f), 180f, -180f)
        }

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawTextOnPath("B U R N", pathBurn, 0f, 0f, paint)
            canvas.nativeCanvas.drawTextOnPath("B O O K", pathBook, 0f, 20f, paint)
        }
    }
}
