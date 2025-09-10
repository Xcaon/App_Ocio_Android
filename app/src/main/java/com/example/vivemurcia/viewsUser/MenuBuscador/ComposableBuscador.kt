package com.example.vivemurcia.viewsUser.MenuBuscador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.ui.theme.colorNegroProyecto
import com.example.vivemurcia.ui.theme.colorPrimario
import com.example.vivemurcia.views.home.ActividadCard
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioBuscador(navController: NavHostController) {

    val buscadorViewModel: BuscadorViewModel = hiltViewModel<BuscadorViewModel>()
    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()

    val listadoAllActividades: List<Actividad> by buscadorViewModel.actividadesAll.collectAsState()
    val listadoFiltrado: List<Actividad> by buscadorViewModel.actividadesFiltradasParaSearch.collectAsState()

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Llamamos al viewModel para que nos devuelva todas las actividades, lo pedimos 1 vez
    LaunchedEffect(Unit) {
        buscadorViewModel.getAllActividades()
    }

    val selectedItems = remember { mutableStateMapOf<EnumCategories, Boolean>().apply {
        EnumCategories.entries.forEach { this[it] = false }
    } }


    val sheetState = rememberModalBottomSheetState() // Para controlar el modal
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = colorPrimario,
                contentColor = Color.White,
                text = {
                    Text(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
                        text = "Filtrar"
                    )
                },
                icon = { Icon(modifier = Modifier.size(24.dp) ,imageVector = Icons.Filled.Tune, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(Color.Red),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = query, // Cada vez que escribe se guarda en esta variable
                onQueryChange = {
                    query =
                        it  // Aqui se define que hacer cada vez que lo actualiza el texto, en este caso actualizamos la query
                },
                onSearch = {
                    buscadorViewModel.buscarActividades(query)  // Cuando el usuario le da al boton de buscar ¿Que debe de hacer?
                },
                active = false,
                onActiveChange = { // Active y ActiveChange es para cerrar el componente cuando se realiza la busqueda
                    active = false
                },
                placeholder = { Text("Buscar actividades...") },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Place, contentDescription = "Actividades")
                },
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White,
                    inputFieldColors = TextFieldDefaults.colors(    // Colores del campo de texto
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Gray,
                        focusedLeadingIconColor = Color.DarkGray,
                        unfocusedLeadingIconColor = Color.LightGray,
                        cursorColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.LightGray
                    )
                ),
                windowInsets = WindowInsets(0, 0, 0, 0)

            ) {}

        }) { innerPadding ->
        if (listadoAllActividades.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(listadoFiltrado) { actividad ->
                    ActividadCard(actividad) {
                        viewModelDetalle.inicialNavController(navController)
                        viewModelDetalle.mostrarActividadDetalle(it)
                    }
                }
            }
        } else {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)), text = "Cargando Actividades...")
                    Espaciado(8)
                    CircularProgressIndicator(color = colorNegroProyecto)
                }
            }
        }

        // Modal para filtrar por categorias
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
                            color = colorNegroProyecto,
                            text = "Filtrar por categorías"
                        )
                    }
                    Espaciado(8)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    Espaciado(16)
                    Column(modifier = Modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                        EnumCategories.entries.forEach { category ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = selectedItems[category] ?: false,
                                    onCheckedChange = { checked ->
                                        selectedItems[category] = checked
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorPrimario,
                                        uncheckedColor = Color.Gray,
                                        checkmarkColor = Color.White
                                    )
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = category.name,
                                    fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
                                    color = colorNegroProyecto,
                                    modifier = Modifier.width(150.dp)
                                )
                            }
                        }

                        Espaciado(24)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {

                            Button(
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorPrimario,
                                    contentColor = Color.White
                                ), onClick = {

                                    scope.launch { sheetState.hide() }.invokeOnCompletion {


                                        // Filtramos las categorías seleccionadas automáticamente
                                        val categorias: List<String> = selectedItems
                                            .filter { it.value }      // Solo los que están seleccionados
                                            .map { it.key.name }      // Tomamos el nombre de la categoría

                                        buscadorViewModel.filtrarActividadesPorCategoria(categorias)

                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }) {
                                Text("Aplicar filtros")
                            }
                        }
                    }
                }
            }
        }
    }

}


