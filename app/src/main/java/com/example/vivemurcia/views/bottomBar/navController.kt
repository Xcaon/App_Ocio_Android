package com.example.vivemurcia.views.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vivemurcia.views.home.InicioHome

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomNavigation(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO.nombre, // Define tu pantalla inicial
            Modifier.padding(innerPadding)
        ) {
            composable(Rutas.INICIO.nombre) {
                InicioHome()
            }

            composable(Rutas.RESERVAS.nombre) {
                Column(Modifier.fillMaxSize().background(Color.Green), verticalArrangement =  Arrangement.Center) {
                    Text("Esto son las reservas")
                }
            }

            composable(Rutas.SEGUIDOS.nombre) {
                Column(Modifier.fillMaxSize().background(Color.Red), verticalArrangement =  Arrangement.Center) {
                    Text("Esto son los seguidos")
                }
            }

            composable(Rutas.CONFIGURACION.nombre) {
                Column(Modifier.fillMaxSize().background(Color.Black), verticalArrangement =  Arrangement.Center) {
                    Text(text = "Esto es la configuración", color = Color.White)
                }
            }

        }
    }
}


