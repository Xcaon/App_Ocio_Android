package com.example.vivemurcia.viewsUser.MenuFavoritos

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.views.home.ActividadCard
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle


@Composable
fun InicioComposableFavoritos(navController: NavController) {

    var viewModel: ViewModelFavoritos = hiltViewModel<ViewModelFavoritos>()
    var favoritos: State<List<Actividad>> = viewModel.favorito.collectAsState()
    val viewModelDetalle : ViewModelDetalle = hiltViewModel<ViewModelDetalle>()

    LaunchedEffect(Unit) {
        viewModel.conseguirFavoritosDelUsuario()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
            fontSize = 18.sp,
            text = "Favoritos",
            textAlign = TextAlign.Center
        )
        Espaciado(16)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(favoritos.value) { actividad ->
                    ActividadCard(
                        actividad
                    ) {
                        viewModelDetalle.inicialNavController(navController)
                        viewModelDetalle.mostrarActividadDetalle(it)
                    }
                }
            })


    }
}