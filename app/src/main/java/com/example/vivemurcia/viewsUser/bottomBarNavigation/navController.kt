package com.example.vivemurcia.views.bottomBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivemurcia.views.config.Inicio
import com.example.vivemurcia.views.home.HomeViewModel
import com.example.vivemurcia.views.home.InicioHome
import com.example.vivemurcia.viewsUser.MenuBuscador.InicioBuscador
import com.example.vivemurcia.viewsUser.MenuFavoritos.InicioComposableFavoritos
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.MostrarActividadDetalle

@Composable
fun MyApp() {
    // Establecemos el controlador de navegación, esto es el inicio de all
    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Rutas.INICIO.nombreRuta,
                    Rutas.FAVORITOS.nombreRuta,
                    Rutas.BUSCADOR.nombreRuta,
                    Rutas.RESERVAS.nombreRuta,
                    Rutas.SEGUIDOS.nombreRuta,
                    Rutas.CONFIGURACION.nombreRuta
                )
            ) {
                MyBottomNavigation(navController)
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO.nombreRuta, // Define tu pantalla inicial
            Modifier.padding(innerPadding)
        ) {

            composable(Rutas.FAVORITOS.nombreRuta) {
                InicioComposableFavoritos(navController)
            }

            // Dentro de cada ruta se pone el contenido que va a enseñar
            composable(Rutas.INICIO.nombreRuta) {
                    backStackEntry ->
                // Recuperamos el ViewModel ligado al NavBackStackEntry de la ruta "INICIO"

                // todo() Ver como mantengo el viewmodel vivo al cambiar de pantallas.
                val homeViewModel = hiltViewModel<HomeViewModel>(backStackEntry)

                InicioHome(navController, homeViewModel)
            }

            composable(Rutas.BUSCADOR.nombreRuta) {
                InicioBuscador(navController)
            }

            composable(Rutas.CONFIGURACION.nombreRuta) {
                    Inicio()
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

//            composable(Rutas.RESERVAS.nombreRuta) {
//                Column(
//                    Modifier
//                        .fillMaxSize()
//                        .background(Color.Green),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text("Esto son las reservas")
//                }
//            }

//            composable(Rutas.SEGUIDOS.nombreRuta) {
//                Column(
//                    Modifier
//                        .fillMaxSize()
//                        .background(Color.Red),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text("Esto son los seguidos")
//                }
//            }



        }

    }
}


