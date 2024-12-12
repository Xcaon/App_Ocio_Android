package com.example.vivemurcia.views.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.fondoPantalla

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListadoAventuras() {

    val listaActividades = listOf(
        Actividad(R.drawable.explorador, "Exploracion", "11:00"),
        Actividad(R.drawable.explorador, "Campo", "11:00"),
        Actividad(R.drawable.explorador, "Huerta", "11:00"),
        Actividad(R.drawable.explorador, "Cipres", "11:00"),
        Actividad(R.drawable.explorador, "Escalada", "11:00"),
        Actividad(R.drawable.explorador, "Natación", "11:00")
    )

    LazyVerticalGrid(columns = GridCells.Fixed(2),  content = {
        items(listaActividades){
            ActividadCard(it)
        }
    })
}

@Composable
fun ActividadCard(actividad: Actividad) {
    Card(border = BorderStroke(0.2.dp, fondoPantalla), modifier = Modifier.width(200.dp).padding(4.dp)) {
        Image(
            painter = painterResource(id = R.drawable.explorador),
            contentDescription = "SuperHero Avatar",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Row (modifier = Modifier.padding( 8.dp , 8.dp, 8.dp, 4.dp)) { Text(text = actividad.horario)  }
        Row ( modifier = Modifier.padding(4.dp, 8.dp, 8.dp, 8.dp)) { Text(text = actividad.titulo) }


    }
}


@Composable
fun ListadoCocina() {
    Log.i("fernando", "Entramos en ListadoCocina")
    Text("cocina")
}

@Composable
fun ListadoRelax() {
    Text("Relax")
}

@Composable
fun ListadoArte() {
    Text("Arte")
}