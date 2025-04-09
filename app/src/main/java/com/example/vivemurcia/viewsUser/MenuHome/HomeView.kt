package com.example.vivemurcia.views.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.views.bottomBar.MyApp
import com.example.vivemurcia.views.bottomBar.Rutas
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VivemurciaTheme {
                MyApp()
            }
        }
    }
}

// Como se pintara cada actividad, es decir, cada card
@Composable
fun ActividadCard(actividad: Actividad, onClickActividad: (Actividad) -> Unit ) {
    Card(
        border = BorderStroke(0.2.dp, fondoPantalla),
        modifier = Modifier
            .width(200.dp)
            .padding(4.dp)
            .height(250.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            // Aqui  lanzamos el proceso para enseñar la actividad
            onClickActividad(actividad)
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
        ) {
            AsyncImage(
                model = actividad.uriImagen,
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.imagenselecccionada)
            )
        }

        // Titulo de la actividad
        Row(
            modifier = Modifier.padding(
                8.dp,
                8.dp,
                8.dp,
                4.dp
            )
        ) { Text(text = actividad.tituloActividad!!) }

        Row(
            modifier = Modifier.padding(
                8.dp,
                8.dp,
                8.dp,
                4.dp
            )
        ) { Text(text = "Dia" + actividad.fechaHoraActividad!!.toDate().day.toString()) }
    }
}


// El loader mientras cargamos las actividades al listado
@Composable
fun CircularProgressIndicatorLoader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}