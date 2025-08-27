package com.example.vivemurcia.viewsUser.MenuBuscador

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.google.firebase.auth.ActionCodeUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscadorViewModel @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {

    private var _actividadesAll = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesAll = _actividadesAll.asStateFlow()

    private var _actividadesFiltradasParaSearch = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesFiltradasParaSearch = _actividadesFiltradasParaSearch.asStateFlow()


//    // Esto se llama solo 1 vez desde el composable
//    fun getAllActividades() {
//        viewModelScope.launch {
//            _actividadesAll.value = fireStoreModel.getAllActividades(999)
//            _actividadesFiltradasParaSearch.value =
//                _actividadesAll.value // Le asignamos los valores para que el listado se alimente
//        }
//    }

    fun buscarActividades(query: String) {
        viewModelScope.launch {
            _actividadesFiltradasParaSearch.value = actividadesAll.value.filter { actividad ->
                actividad.tituloActividad?.contains(query, ignoreCase = true) ?: false
            }
        }
    }

    fun filtrarActividadesPorCategoria(categorias: List<String>) {

        if (categorias.isNotEmpty()) {
            _actividadesFiltradasParaSearch.value = actividadesAll.value
            // Filtramos por las categorias buscadas y actualizamos los valores de la lista
            _actividadesFiltradasParaSearch.value =
                actividadesFiltradasParaSearch.value.filter { actividad ->
                    actividad.categoriaActividad.toString() in categorias
                }
        } else {
            // Si no hay categorias seleccionadas, mostramos todas las actividades
            _actividadesFiltradasParaSearch.value = actividadesAll.value
        }
    }


}