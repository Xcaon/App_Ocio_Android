package com.example.vivemurcia.views.home

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Tab
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.clases.Categoria
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle


@Composable
fun InicioHome(navController: NavController) {

    val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()

    var listadoHorizontal: State<List<Actividad>> =
        homeViewModel.actividadesDestacadas.collectAsState()
    var actividadesTodos: State<List<Actividad>> = homeViewModel.actividadesTodas.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getActividadesDestacadas()
        homeViewModel.getAllActividades()
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(enabled = true,state =rememberScrollState())) {
        // 1 APARTADO : Texto inicial con boton para filtrar categorias ///////////////////////////////////////
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
                    fontSize = 24.sp,
                    text = "Explora",
                    textAlign = TextAlign.Center
                )
            }
        }

        // 2 APARTADO: Destacados ////////////////////////////////////////////
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row() {
                Text(
                    fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
                    fontSize = 18.sp,
                    text = "Destacados",
                    textAlign = TextAlign.Center
                )
            }
            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)

            Row(modifier = Modifier.height(200.dp)) {
                if (listadoHorizontal.value.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(listadoHorizontal.value) { actividad ->
                            ActividadCardCuadrada(actividad) {
                                viewModelDetalle.inicialNavController(navController)
                                viewModelDetalle.mostrarActividadDetalle(it)
                            }
                        }
                    }

                } else {
                    Box(
                        modifier = Modifier
                            .background(shimmerBrush())
                            .fillMaxWidth().height(200.dp)
                    ) { }
                }
            }
        }

        // 3 APARTADO: Actividades Novedades //////////////////////////////
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
                fontSize = 18.sp,
                text = "Novedades",
                textAlign = TextAlign.Center
            )
            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
            if (actividadesTodos.value.isNotEmpty()) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2), // 2 filas
                    modifier = Modifier
                        .height(400.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(actividadesTodos.value) { actividad ->
                        ActividadCard(actividad) {
                            viewModelDetalle.inicialNavController(navController)
                            viewModelDetalle.mostrarActividadDetalle(it)
                        }
                    }
                }
            } else if (actividadesTodos.value.isEmpty()) {
                Row(
                    modifier = Modifier
                        .background(shimmerBrush())
                        .fillMaxWidth()
                        .height(400.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    content = {

                    }
                )
            }

        }


    }

}





