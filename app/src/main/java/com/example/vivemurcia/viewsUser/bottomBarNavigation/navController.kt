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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivemurcia.views.home.InicioHome
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.MostrarActividadDetalle

@Composable
fun MyApp() {
    // Establecemos el controlador de navegación, esto es el inicio de all
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomNavigation(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO.nombre, // Define tu pantalla inicial
            Modifier.padding(innerPadding)
        ) {

            // Dentro de cada ruta se pone el contenido que va a enseñar
            composable(Rutas.INICIO.nombre) {
                InicioHome(navController)
            }

            composable(Rutas.RESERVAS.nombre) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Esto son las reservas")
                }
            }

            composable(Rutas.SEGUIDOS.nombre) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Esto son los seguidos")
                }
            }

            composable(Rutas.CONFIGURACION.nombre) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Esto es la configuración", color = Color.White)
                }
            }

            composable(
                "detalle/{tituloActividad}/{localizacionActividad}",
                arguments = listOf(
                    navArgument("tituloActividad") { type = NavType.StringType },
                    navArgument("localizacionActividad") { type = NavType.StringType }
                )) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    verticalArrangement = Arrangement.Center
                ) {
                    // 👇 AQUÍ es lo que me preguntabas:
                    val tituloActividad = it.arguments?.getString("tituloActividad")
                    val localizacionActividad = it.arguments?.getString("localizacionActividad")
                    MostrarActividadDetalle(tituloActividad, localizacionActividad)
                }
            }

        }

    }
}


