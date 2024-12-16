package com.example.vivemurcia.views.login

import android.util.Log
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivemurcia.R
import com.example.vivemurcia.ui.theme.colorPrimario



@Composable
fun InitLogin(onButtonClicked: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.fondologin),
            contentDescription = null, // O proporciona una descripción de la imagen
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Contenido(onButtonClicked)

    }
}

@Composable
fun Contenido(onButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal =  16.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box ( modifier = Modifier.weight(1f)) {
            TitleText()
        }
        Box ( modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            InicioSesionElementos(onButtonClicked)
        }
        Box ( modifier = Modifier.weight(1f)) {

        }
    }
}

@Composable
fun TitleText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "VIVE",
            color = Color.White,
            fontSize = 150.sp,
            fontFamily = FontFamily(Font(R.font.barlowblackitalic))
        )
        Text(
            text = "MURCIA",
            color = Color.White,
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(R.font.barloblack))
        )
    }
}

@Composable
fun InicioSesionElementos(onButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BIENVENIDO DE NUEVO",
            color = Color.White,
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.notosansbold))
        )

        Button(
            onClick = onButtonClicked,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(colorPrimario),
            enabled = true
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.iconogoogle),
                    contentDescription = "Icono de favorito"
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Inicia Sesión Con Google",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.notosansbold))
                )
            }

        }
        Text(
            text = "Iniciar sesión para continuar",
            color = Color.White,
            fontSize = 21.sp,
            fontFamily = FontFamily(Font(R.font.notosanssemibold))
        )
    }
}