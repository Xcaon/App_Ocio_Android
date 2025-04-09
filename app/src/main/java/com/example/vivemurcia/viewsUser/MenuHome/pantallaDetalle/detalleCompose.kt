package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MostrarActividadDetalle(actividadTitulo: String?, localizacionActividad: String?) {


    Text(text = actividadTitulo.toString())
    Text(text = localizacionActividad.toString())

}