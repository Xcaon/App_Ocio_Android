package com.example.vivemurcia.views.home

import androidx.compose.material3.Tab
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.R
import com.example.vivemurcia.model.dataClass.TabItem
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.viewsUser.MenuHome.tabArte.ListadoArte
import com.example.vivemurcia.viewsUser.MenuHome.tabAventuras.ListadoAventuras
import com.example.vivemurcia.viewsUser.MenuHome.tabCocina.ListadoCocina
import com.example.vivemurcia.viewsUser.MenuHome.tabRelax.ListadoRelax


@Composable
fun InicioHome(navController : NavController) {
    var selectedTabIndex: Int by remember { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(fondoPantalla)
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .background(Color.White)
        )
        {
            FiltroActividades()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
        ) {
            // Empieza en 0 porque es el primer tab
            CategoriasTab(selectedTabIndex) { updateSelectedTab ->
                selectedTabIndex = updateSelectedTab
            }
        }
        Listado(selectedTabIndex,navController)
    }
}

@Composable
fun CategoriasTab(selectedTabIndexActual: Int, selectedTabIndexUpdate: (Int) -> Unit) {

    val tabs = listOf(
        TabItem(EnumCategories.AVENTURAS.nombre, R.drawable.atle),
        TabItem(EnumCategories.COCINA.nombre, R.drawable.cocina),
        TabItem(EnumCategories.RELAX.nombre, R.drawable.relax),
        TabItem(EnumCategories.ARTE.nombre, R.drawable.arte)
    )

    Column {
        TabRow(selectedTabIndex = selectedTabIndexActual) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(tabs[index].title, color = Color.Black) },
                    selected = selectedTabIndexActual == index, // Marcar como seleccionado
                    onClick = { selectedTabIndexUpdate(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = tabs[index].icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            }
        }
    }

}

@Composable
fun Listado(selectedTabIndex: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (selectedTabIndex) {
            0 -> Column { ListadoAventuras(navController) }
            1 -> Column { ListadoCocina(navController) }
            2 -> Column { ListadoRelax(navController) }
            3 -> Column { ListadoArte(navController) }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltroActividades() {

    val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()

    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    // Investigar el espaciado que deja en la interfaz TODO()
        SearchBar(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = {
                homeViewModel.filterActividades(it)
            },
            active = isSearching,
            onActiveChange = {},
            content = {},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            placeholder = { Text("Buscar actividad") }
        )
}

