package com.example.vivemurcia.views.bottomBar

import android.content.Intent
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

// Este fichero define el diseño del menu inferior
// Usamos el navController para gestionar la ruta a la hora de pulsar en un icono

@Composable
fun MyBottomNavigation(navController: NavHostController) {

    NavigationBar(contentColor = MaterialTheme.colorScheme.primary) {
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
            label = { Text(text = "Inicio") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio de la app"
                )
            })

        NavigationBarItem(
            selected = index == 1,
            onClick = {
                index = 1
                navController.navigate(Rutas.RESERVAS.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(text = "Reservas") },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Reservas de la app"
                )
            })

        NavigationBarItem(
            selected = index == 2,
            onClick = {
                index = 2
                navController.navigate(Rutas.SEGUIDOS.nombreRuta) {

                    // launchSingleTop = true indica al NavController que si la pantalla Home ya está en la
                    // parte superior del back stack, no se creará una nueva instancia. En su lugar, se reutilizará la instancia existente.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            label = { Text(text = "Seguidos") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "seguidos"
                )
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
            label = { Text(text = "Configuración") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "configuracion"
                )
            })
    }


}







