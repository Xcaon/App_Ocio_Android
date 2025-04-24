package com.example.vivemurcia.views.info

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivemurcia.R
import com.example.vivemurcia.ui.theme.colorNegroProyecto
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.ui.theme.fondoPasosInfo
import com.example.vivemurcia.ui.theme.textoNaranja

@Composable
fun InicioComposableInfoView(onclick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .background(fondoPantalla),
        contentAlignment = Alignment.Center
    ) {
        Contenido(onclick)
    }
}


@Composable
fun Contenido(onclick: () -> Unit) {

    Column() {
        Box(
            Modifier
                .weight(1.2f)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                val painter =
                    painterResource(R.drawable.icononotis) // Reemplaza con el nombre de tu archivo PNG
                Image(
                    painter = painter,
                    modifier = Modifier
                        .size(width = 50.dp, height = 50.dp)
                        .padding(bottom = 4.dp),
                    contentDescription = "Notificaciones"
                )
                Text(
                    "¡Personaliza tus notificaciones!",
                    fontFamily = FontFamily(Font(R.font.notosansbold)),
                    color = colorNegroProyecto,
                    fontSize = 22.sp,
                    maxLines = 1
                )
                Text(
                    "Aprende a seguir tus locales y actividades favoritas",
                    fontFamily = FontFamily(Font(R.font.notosansregular)),
                    fontSize = 14.sp
                )
            }
        }
        Box(
            Modifier
                .weight(1.3f)
                .fillMaxWidth()
        ) {
            Card(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.2f)) {
                        Column(Modifier.fillMaxWidth()) {
                            val painter = painterResource(R.drawable.icestrella)
                            Image(
                                painter = painter,
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .padding(bottom = 4.dp),
                                contentDescription = "Descripción de la imagen"
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(0.8f)) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                "¡Es muy fácil empezar!",
                                fontFamily = FontFamily(Font(R.font.notosanssemibold))
                            )
                            Text("Sigue estos pasos para no perderte ninguna novedad")
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .weight(1.3f)
                .fillMaxWidth()
        ) {
            Card(
                onClick = {}, modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.2f)) {
                        Column(Modifier.fillMaxWidth()) {
                            val painter = painterResource(R.drawable.icubicacioninfo)
                            Image(
                                painter = painter,
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .padding(bottom = 4.dp),
                                contentDescription = "Descripción de la imagen"
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(0.8f)) {
                        Column(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .background(fondoPasosInfo)
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        "Paso 1",
                                        fontFamily = FontFamily(
                                            Font(R.font.notosansblack)
                                        ),
                                        color = textoNaranja,
                                        fontSize = 12.sp
                                    )
                                }
                                Text(
                                    text =
                                        "Encuentra tu local favorito",
                                    Modifier.padding(start = 8.dp),
                                    fontFamily = FontFamily(Font(R.font.notosanssemibold))
                                )
                            }
                            Text("Explora los mejores lugares y actividades de  tu ciudad")
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .weight(1.3f)
                .fillMaxWidth()
        ) {
            Card(
                onClick = {}, modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.2f)) {
                        Column(Modifier.fillMaxWidth()) {
                            val painter = painterResource(R.drawable.icestrella)
                            Image(
                                painter = painter,
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .padding(bottom = 4.dp),
                                contentDescription = "Descripción de la imagen"
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(0.8f)) {
                        Column(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .background(fondoPasosInfo)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(
                                        "Paso 2",
                                        fontFamily = FontFamily(
                                            Font(R.font.notosansblack)
                                        ),
                                        color = textoNaranja,
                                        fontSize = 12.sp
                                    )
                                }
                                Text(
                                    text =
                                        "Pulsa “Seguir”",
                                    Modifier.padding(start = 8.dp),
                                    fontFamily = FontFamily(Font(R.font.notosanssemibold))
                                )
                            }
                            Text("Busca el botón de suscripción en el perfil del Local")
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .weight(1.3f)
                .fillMaxWidth()
        ) {
            Card(
                onClick = {}, modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.2f)) {
                        Column(Modifier.fillMaxWidth()) {
                            val painter = painterResource(R.drawable.iceventosinfo)
                            Image(
                                painter = painter,
                                modifier = Modifier
                                    .size(width = 50.dp, height = 50.dp)
                                    .padding(bottom = 4.dp),
                                contentDescription = "Descripción de la imagen"
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(0.8f)) {
                        Column(Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .background(fondoPasosInfo)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(
                                        "Paso 3",
                                        fontFamily = FontFamily(
                                            Font(R.font.notosansblack)
                                        ),
                                        color = textoNaranja,
                                        fontSize = 12.sp
                                    )
                                }
                                Text(
                                    text =
                                        "¡Listo!",
                                    Modifier.padding(start = 8.dp),
                                    fontFamily = FontFamily(Font(R.font.notosanssemibold))
                                )
                            }
                            Text("Recibirás notificaciones de eventos y novedades")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .weight(1.5f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF37B22), Color(0xFFEFA120)),
                            start = Offset(0f, Float.POSITIVE_INFINITY), // 270º
                            end = Offset(0f, 0f) // 90º
                        )
                    ),
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp) // Establece la forma
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Transparent)
                        .fillMaxSize()
                ) {
                    Text(
                        text =
                            "¡Empieza a explorar tus lugares favoritos!\n\n" +
                                    "Personaliza tu experiencia y mantente al día de todo lo que suce en tu ciudada",
                        fontFamily = FontFamily(Font(R.font.notosanssemibold)),
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(32.dp)).clickable{onclick()},
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = "Comenzar a explorar",
                            fontFamily = FontFamily(Font(R.font.notosansbold)),
                            color = colorNegroProyecto,
                            fontSize = 16.sp
                        )
                        val painter =
                            painterResource(id = R.drawable.icarrow) // Reemplaza con el nombre de tu vector
                        Icon(
                            painter = painter,
                            contentDescription = "Flecha",
                            tint = Color.Unspecified // Mantén el color original del vector
                        )
                    }
                }
            }
        }


    }
}
