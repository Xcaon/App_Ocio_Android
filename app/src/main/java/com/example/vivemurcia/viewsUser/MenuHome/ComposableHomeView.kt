package com.example.vivemurcia.views.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material3.Tab
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vivemurcia.model.clases.Categoria
import com.example.vivemurcia.model.dataClass.TabItem
import com.example.vivemurcia.ui.theme.colorNegroProyecto
import com.example.vivemurcia.ui.theme.grisTransparente
import com.example.vivemurcia.viewsUser.MenuHome.listadoCategoriasHome


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
        var categorias : List<Categoria> = emptyList()
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

    val viewModelHome : HomeViewModel = hiltViewModel<HomeViewModel>()
    val isLoading by viewModelHome.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        viewModelHome.getCategoriasTab()
    }

    var categorias: State<List<Categoria>> = viewModelHome.categorias.collectAsState()
    Log.i("fer", "Estas son las categorias del stateFlow $categorias")

    if (isLoading) {
        CircularProgressIndicatorLoader()
        return
    }

    var tabs: List<TabItem> = categorias.value.map { categoria : Categoria ->
        TabItem(categoria.nombre.toString(), categoria.iconoUri.toString())
    }

        Column {
        TabRow(modifier = Modifier.clickable{focusManager.clearFocus()},selectedTabIndex = selectedTabIndexActual) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(fontSize = 14.sp, text = tabs[index].title) },
                    selected = selectedTabIndexActual == index, // Marcar como seleccionado
                    onClick = { selectedTabIndexUpdate(index) },
                    icon = {
                        AsyncImage(
                            model = title.icon, // aquí pones la URL o el recurso
                            contentDescription = "Icono de ${title.title}",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                )
            }
        }
            HorizontalDivider( thickness = 1.dp, color = grisTransparente)
    }

}

@Composable
fun Listado(selectedTabIndex: Int, navController: NavController) {

    val homeViewModel : HomeViewModel = hiltViewModel<HomeViewModel>()
    var categorias: State<List<Categoria>> = homeViewModel.categorias.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (!categorias.value.isEmpty()) {
            var categoria = categorias.value[selectedTabIndex]
            listadoCategoriasHome(navController, categoria.nombre.toString())
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
                Text(fontSize = 16.sp ,text = "¿Qué plan te llama hoy?",overflow = TextOverflow.Ellipsis)
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

