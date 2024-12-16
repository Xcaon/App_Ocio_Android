package com.example.vivemurcia.views.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.fechas.Calendario
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.example.vivemurcia.ui.theme.fondoPantalla
import kotlinx.coroutines.delay


@Composable
fun ListadoAventuras() {

    var isLoading by remember { mutableStateOf(true) }
    val circularProgressIndicatorLoader = remember {
        @Composable { CircularProgressIndicatorLoader() }
    }

    val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
    val actividades: List<Actividad> = homeViewModel.actividades.collectAsState().value


    if (isLoading) {
        circularProgressIndicatorLoader()

        // Ejecuto la actualizacion de la lista manualmente
        LaunchedEffect(Unit) {
            homeViewModel.getActividades()
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(actividades) {
                ActividadCard(it)
            }
        })
        isLoading = false
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(actividades) {
                ActividadCard(it)
            }
        })
    }

}

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


@Composable
fun ActividadCard(actividad: Actividad) {
    Card(
        border = BorderStroke(0.2.dp, fondoPantalla), modifier = Modifier
            .width(200.dp)
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.explorador),
            contentDescription = "SuperHero Avatar",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )


        // Titulo de la actividad
        Row(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 4.dp)) { Text(text = actividad.titulo!!) }

        var fechaDia = Calendario.getDayOfMonth(actividad.horario!!.toDate())

        // Fecha de la actividad
        Row(modifier = Modifier.padding(4.dp, 8.dp, 8.dp, 8.dp)) { Text(text = "Día $fechaDia") }


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