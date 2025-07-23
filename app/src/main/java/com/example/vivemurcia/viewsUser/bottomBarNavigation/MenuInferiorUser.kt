package com.example.vivemurcia.views.bottomBar

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Este fichero define el diseño del menu inferior
// Usamos el navController para gestionar la ruta a la hora de pulsar en un icono

@Composable
fun MyBottomNavigation(navController: NavHostController) {


    NavigationBar( modifier = Modifier
        .drawBehind {
            drawLine(
                color = Color.LightGray, // o MaterialTheme.colorScheme.outline
                start = Offset(0f, 0f), // izquierda arriba
                end = Offset(size.width, 0f), // derecha arriba
                strokeWidth = 2f // grosor de la línea
            )
        }, contentColor = MaterialTheme.colorScheme.onSurface, containerColor = MaterialTheme.colorScheme.surface ) {
        var index by remember { mutableIntStateOf(0) }
        NavigationBarItem(
            selected = index == 0,
            onClick = {
                index = 0
                navController.navigate(Rutas.INICIO.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(fontSize = 12.sp, text = "Inicio") },
            icon = {
                if ( index == 0) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Inicio de la app")
                } else {
                    Icon(imageVector = Icons.Outlined.Home, contentDescription = "Inicio de la app")
                }
            })



        NavigationBarItem(
            selected = index == 4,
            onClick = {
                index = 4
                navController.navigate(Rutas.FAVORITOS.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(fontSize = 12.sp, text = "Favoritos") },
            icon = {
                if (index == 4) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Pantalla de favoritos"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Pantalla de favoritos"
                    )
                }
            })

        NavigationBarItem(
            selected = index == 5,
            onClick = {
                index = 5
                navController.navigate(Rutas.BUSCADOR.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(fontSize = 12.sp, text = "Buscar") },
            icon = {
                if ( index == 5) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscador de actividades")
                } else {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Buscador de actividades")
                }
            })

        NavigationBarItem(
            selected = index == 3,
            onClick = {
                index = 3
                navController.navigate(Rutas.CONFIGURACION.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(fontSize = 12.sp, text = "Perfil") },
            icon = {
                if ( index == 3) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "configuracion")
                } else {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "configuracion")
                }
            })

//        NavigationBarItem(
//            selected = index == 2,
//            onClick = {
//                index = 2
//                navController.navigate(Rutas.SEGUIDOS.nombreRuta) {
//
//                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
//                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
//                    launchSingleTop = true
//                    // Restore state when reselecting a previously selected item
//                    restoreState = true
//                }
//            },
//            label = { Text(fontSize = 12.sp,text = "Seguidos") },
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Notifications,
//                    contentDescription = "seguidos"
//                )
//            })

//        NavigationBarItem(
//            selected = index == 1,
//            onClick = {
//                index = 1
//                navController.navigate(Rutas.RESERVAS.nombreRuta) {
//
//                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
//                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
//                    launchSingleTop = true
//                    // Restore state when reselecting a previously selected item
//                    restoreState = true
//                }
//            },
//            label = { Text(fontSize = 12.sp,text = "Reservas") },
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Reservas de la app"
//                )
//            })


    }


}







