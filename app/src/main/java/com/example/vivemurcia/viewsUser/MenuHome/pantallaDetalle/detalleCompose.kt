package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MostrarActividadDetalle(idActividad: String?, categoriaActividad: String?) {

    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()
    val actividad  = viewModelDetalle.actividad.collectAsState()


    LaunchedEffect(idActividad, categoriaActividad) {
        viewModelDetalle.PintarActividadDetalle(idActividad.toString(), categoriaActividad)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // Aqui ira la imagen
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp))
        {

        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp))
        {
            Text(text = "El id es ${actividad.value}")}
            Button(modifier = Modifier.padding(horizontal = 12.dp), onClick = { /*TODO*/ }) {
                Text(text = "Como llegar")

            }
        }
    }
