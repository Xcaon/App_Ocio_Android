package com.example.vivemurcia.views.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

////Enum lo usamos para crear constantes pero sin añadir logica
//enum class Rutas(val nombre: String) {
//    INICIO("inicio"),
//    RESERVAS("reservas"),
//    SEGUIDOS("seguidos"),
//    CONFIGURACION("configuracion"),
//    DETALLE("detalle")
//}

// La sealed class son como los enums pero se pueden añadir comportamiento como funciones
sealed class Rutas(val nombreRuta: String) {
    object INICIO : Rutas("inicio")
    object RESERVAS : Rutas("reservas")
    object SEGUIDOS : Rutas("seguidos")
    object BUSCADOR : Rutas("buscador")
    object CONFIGURACION : Rutas("configuracion")
    object DETALLE : Rutas("detalles/{idActividad}/{categoriaActividad}") {
        fun crearRuta(idActividad: String, categoriaActividad:String) = "detalles/$idActividad/$categoriaActividad"
    }
    object FAVORITOS : Rutas("favoritos")
}


