package com.example.vivemurcia.views.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

//Enum lo usamos para crear constantes pero sin añadir logica
enum class Rutas(val nombre: String) {
    INICIO("inicio"),
    RESERVAS("reservas"),
    SEGUIDOS("seguidos"),
    CONFIGURACION("configuracion")
}

//// La sealed class son como los enums pero con funcionalidades extendidas
//sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
//    object Inicio : Screen("inicio", "Home", Icons.Default.Home)
//    object Reservas : Screen("reservas", "Profile", Icons.Default.DateRange)
//    object Seguidos : Screen("seguidos", "Settings", Icons.Default.Favorite)
//    object Configuracion : Screen("configuracion", "Settings", Icons.Default.Settings)
//}