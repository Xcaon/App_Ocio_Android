package com.example.vivemurcia.views.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {



    // MutableLiveData: Permite actualizar el valor de la lista de actividades
    private val _actividades = MutableStateFlow<List<Actividad>>(emptyList())

    //LiveData: Permite que otros componentes observen los cambios en la lista de actividades sin tener acceso directo para modificarla
    val actividades: StateFlow<List<Actividad>> = _actividades.asStateFlow()

    fun getActividades() {
        viewModelScope.launch {
            try {
                val nuevasActividades = fireStoreModel.getAveturasCollection()

                _actividades.value = nuevasActividades
            } catch (e: Exception) {
                Log.i(
                    "fernando",
                    "Error al obtener las actividades de aventuras 'HomeViewModel'" + e.message.toString()
                )
            }
        }
    }

}