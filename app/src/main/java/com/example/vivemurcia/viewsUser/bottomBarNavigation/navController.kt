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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivemurcia.views.config.Inicio
import com.example.vivemurcia.views.home.InicioHome
import com.example.vivemurcia.viewsUser.MenuFavoritos.InicioComposableFavoritos
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.MostrarActividadDetalle
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle

@Composable
fun MyApp() {
    // Establecemos el controlador de navegación, esto es el inicio de all
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomNavigation(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO.nombreRuta, // Define tu pantalla inicial
            Modifier.padding(innerPadding)
        ) {

            composable(Rutas.FAVORITOS.nombreRuta) {
                InicioComposableFavoritos()
            }

            // Dentro de cada ruta se pone el contenido que va a enseñar
            composable(Rutas.INICIO.nombreRuta) {
                val focusManager = LocalFocusManager.current
                InicioHome(navController)
            }

            composable(Rutas.RESERVAS.nombreRuta) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Esto son las reservas")
                }
            }

            composable(Rutas.SEGUIDOS.nombreRuta) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Esto son los seguidos")
                }
            }

            composable(Rutas.CONFIGURACION.nombreRuta) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    verticalArrangement = Arrangement.Center
                ) {
                    Inicio()
                }
            }

            composable(
                route = Rutas.DETALLE.nombreRuta,
                arguments = listOf(navArgument("idActividad") { type = NavType.StringType },
                    navArgument("categoriaActividad") { type = NavType.StringType }
                )) { NavBackStackEntry ->

                    val idActividad = NavBackStackEntry.arguments?.getString("idActividad")
                    val categoriaActividad = NavBackStackEntry.arguments?.getString("categoriaActividad")

                    MostrarActividadDetalle(idActividad, categoriaActividad)

            }

        }

    }
}


