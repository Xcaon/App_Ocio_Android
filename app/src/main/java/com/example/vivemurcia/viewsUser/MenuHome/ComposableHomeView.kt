package com.example.vivemurcia.views.home

import androidx.compose.material3.Tab
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.R
import com.example.vivemurcia.model.dataClass.TabItem
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.ui.theme.colorNegroProyecto
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.viewsUser.MenuHome.tabArte.ListadoArte
import com.example.vivemurcia.viewsUser.MenuHome.tabAventuras.ListadoAventuras
import com.example.vivemurcia.viewsUser.MenuHome.tabCocina.ListadoCocina
import com.example.vivemurcia.viewsUser.MenuHome.tabRelax.ListadoRelax


@Composable
fun InicioHome(navController: NavController) {
    var selectedTabIndex: Int by remember { mutableIntStateOf(0) }
    val focusManager = LocalFocusManager.current
    Column(
        Modifier
            .fillMaxSize().clickable {
                focusManager.clearFocus()
            }
    ) {
        // Barra de busqueda
        Row() {

            FiltroActividades()
        }
        // Barra de categorias
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            // Empieza en 0 porque es el primer tab
            CategoriasTab(focusManager,selectedTabIndex) { updateSelectedTab ->
                selectedTabIndex = updateSelectedTab
            }
        }
        // Listado de Actividades
        Listado(selectedTabIndex, navController)
    }
}

@Composable
fun CategoriasTab(focusManager: FocusManager,selectedTabIndexActual: Int, selectedTabIndexUpdate: (Int) -> Unit) {

    val tabs = listOf(
        TabItem(EnumCategories.AVENTURAS.nombre, R.drawable.atle),
        TabItem(EnumCategories.COCINA.nombre, R.drawable.cocina),
        TabItem(EnumCategories.RELAX.nombre, R.drawable.relax),
        TabItem(EnumCategories.ARTE.nombre, R.drawable.arte)
    )

    Column {
        TabRow(modifier = Modifier.clickable{focusManager.clearFocus()},selectedTabIndex = selectedTabIndexActual) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(fontSize = 12.sp, text = tabs[index].title) },
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

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false,
                ambientColor = colorNegroProyecto
            ),
        placeholder = {
            if (!isFocused && searchText.isEmpty()) {
                Text("¿Qué te apetece este fin de semana?")
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = { homeViewModel.filterActividades(searchText) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                homeViewModel.filterActividades(searchText)
            }
        ),
        interactionSource =  interactionSource
    )
}

