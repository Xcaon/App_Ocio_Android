package com.example.vivemurcia.views.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.clases.Categoria
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.text.contains
import kotlin.text.isBlank

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {

    init {
        getCategoriasTab()
    }

    val _categorias: MutableStateFlow<List<Categoria>> = MutableStateFlow<List<Categoria>>(emptyList())
    var categorias: StateFlow<List<Categoria>> = _categorias.asStateFlow()

    val _actividadesPorCategoria: MutableMap<EnumCategories, MutableStateFlow<List<Actividad>>> =
        EnumCategories.entries.associateWith { categoria ->
            MutableStateFlow<List<Actividad>>(getActividadesFlow(categoria))
        }.toMutableMap()

    val actividadesPorCategoria: Map<EnumCategories, StateFlow<List<Actividad>>> =
        _actividadesPorCategoria.mapValues { it.value.asStateFlow() }

    private val actividadesOriginalesPorCategoria: MutableMap<EnumCategories, List<Actividad>> =
        EnumCategories.entries.associateWith {
            emptyList<Actividad>()
        }.toMutableMap()

    fun getActividadesFlow(categoria: EnumCategories): List<Actividad> {

        // Actividad vacia para rellenarla con la petición a Firestore
        var nuevasActividades: List<Actividad> = emptyList<Actividad>()

        // Hacemos la peticion de los datos a firebase
        viewModelScope.launch {
            try {
                // Obtenemos las actividades de Firestore
                nuevasActividades = fireStoreModel.getActividades(categoria)

                // Actualizamos el listado de cada categoria
                _actividadesPorCategoria[categoria]?.value = nuevasActividades
                actividadesOriginalesPorCategoria[categoria] = nuevasActividades

            } catch (e: Exception) { Log.i("fernando", "Error al obtener las actividades de aventuras 'HomeViewModel'" + e.message.toString()) }
        }
        return nuevasActividades
    }


    // PENDIENTE DE ADAPTAR CON TODOS LOS FILTROS
    // Actualizar actividades según el filtro
    fun filterActividades(query: String) {
        if (query.isBlank()) { // Si no se inserta nada o se reinicia cargamos todos las actividades originales
            EnumCategories.entries.forEach { categoria ->
                _actividadesPorCategoria[categoria]!!.value =
                    actividadesOriginalesPorCategoria[categoria]!!
            }
        } else { //
            EnumCategories.entries.forEach { categoria ->
                _actividadesPorCategoria[categoria]!!.value = actividadesOriginalesPorCategoria[categoria]!!.filter { actividad ->
                        actividad.tituloActividad!!.contains(query, ignoreCase = true)
                    }
            }
        }
    }

      fun getCategoriasTab() {
          viewModelScope.launch {
              var categorias: List<Categoria> = fireStoreModel.getCategorias()
              Log.i("tabs", "Estas son las categorias: $categorias")
              _categorias.value = categorias
          }
    }




}


