package com.example.vivemurcia.views.home

import android.content.res.Resources
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.views.bottomBar.MyApp
import com.example.vivemurcia.views.bottomBar.Rutas
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
            .background(Color.Transparent),
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
            modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 2.dp
            )
        ) { Text(style = MaterialTheme.typography.bodySmall, fontSize = 16.sp, text = actividad.tituloActividad!!.replaceFirstChar { it.uppercase() }) }

        // Fecha y hora
        val timestamp: Timestamp = actividad.fechaHoraActividad!! // de Firebase
        val calendar = Calendar.getInstance().apply {
            time = timestamp.toDate()
        }
        val formato = SimpleDateFormat("d MMMM", Locale("es", "ES"))
        val fechaBonita = formato.format(calendar.time)

        Row(
            modifier = Modifier.padding(8.dp, 2.dp, 8.dp, 4.dp)
        ) { Text(fontSize = 14.sp, text = "$fechaBonita") }
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