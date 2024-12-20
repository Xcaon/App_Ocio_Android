package com.example.vivemurcia.views.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.text.contains
import kotlin.text.filter
import kotlin.text.isBlank

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {

    // MutableLiveData: Permite actualizar el valor de la lista de actividades

    private val _actividades = MutableStateFlow<List<Actividad>>(getActividades())

    //LiveData: Permite que otros componentes observen los cambios en la lista de actividades sin tener acceso directo para modificarla
    val actividades: StateFlow<List<Actividad>> = _actividades.asStateFlow()

    // Lista original para conservar las actividades completas
    var actividadesOriginales: List<Actividad> = emptyList()


    fun getActividades(): List<Actividad> {
        var nuevasActividades: List<Actividad> = emptyList<Actividad>()
        viewModelScope.launch {
            try {
                nuevasActividades = fireStoreModel.getAveturasCollection()
                actividadesOriginales = nuevasActividades
                _actividades.value = nuevasActividades

            } catch (e: Exception) {
                Log.i(
                    "fernando",
                    "Error al obtener las actividades de aventuras 'HomeViewModel'" + e.message.toString()
                )
            }
        }
        return nuevasActividades
    }

    // Actualizar actividades según el filtro
    fun filterActividades(query: String) {
        if (query.isBlank()) {
            _actividades.value = actividadesOriginales // Sin filtro
        } else {
            _actividades.value = actividadesOriginales.filter { actividad ->
                actividad.tituloActividad!!.contains(query, ignoreCase = true)
            }
        }
    }

}


