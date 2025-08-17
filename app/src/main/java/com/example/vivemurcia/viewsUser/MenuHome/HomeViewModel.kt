package com.example.vivemurcia.views.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {

    var _actividadesDestacadas = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesDestacadas = _actividadesDestacadas.asStateFlow()

    var _actividadesTodas = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesTodas = _actividadesTodas.asStateFlow()

    fun getActividadesDestacadas() {

        if ( _actividadesDestacadas.value.isNotEmpty()) return
        Log.d("HomeViewModel", "getActividadesDestacadas: ${actividadesDestacadas.value} ")
        viewModelScope.launch {

            // Le decimos que destacadas queremos
            var listaPeticion : List<String> = listOf("sCvtbUBSG6EE80RzRMYg", "3gFXlXwKzOg4Z6reHbOf", "937fEpccGoduIraSF48k", "MhzDNdqort8yIlsDvLqY", "Ue518MOgVtRF2PZr2eBp")
            // Hacemos la peticion a fireStore
            var destacadas = fireStoreModel.getDestacadas(listaPeticion)

            _actividadesDestacadas.value = destacadas
        }
    }


    fun getAllActividades(){

        if ( _actividadesTodas.value.isNotEmpty() ) return

        viewModelScope.launch {
            _actividadesTodas.value = fireStoreModel.getAllActividades(12)
        }
    }

}


