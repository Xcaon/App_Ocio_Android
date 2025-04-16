package com.example.vivemurcia.viewsUser.MenuHome.tabRelax

import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.views.home.ActividadCard
import com.example.vivemurcia.views.home.CircularProgressIndicatorLoader
import com.example.vivemurcia.views.home.HomeViewModel
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle

@Composable
fun ListadoRelax(navController: NavController?) {

    val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
    val viewModelDetalle : ViewModelDetalle = hiltViewModel<ViewModelDetalle>()

    // Obtenemos las actividades de Firestore
    val actividades: List<Actividad> by homeViewModel.actividadesRelax.collectAsState()

//    Log.w("fernando", "Esta es la lista $actividades")
    Espaciado(16)
    if (actividades.isEmpty()) {
        CircularProgressIndicatorLoader()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(actividades) {
                ActividadCard(it) {
                    viewModelDetalle.inicialNavController(navController!!)
                    viewModelDetalle.mostrarActividadDetalle(it)
                }
            }
        })
    }
}