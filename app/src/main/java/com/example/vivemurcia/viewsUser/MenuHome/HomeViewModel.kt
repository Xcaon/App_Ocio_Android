package com.example.vivemurcia.views.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.data.Repository.ActividadesRepository
import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fireStoreModel: FireStoreModel,
    private val actividadesRepository: ActividadesRepository,
    private val db: AppDatabase
) : ViewModel() {

    var _actividadesDestacadas = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesDestacadas = _actividadesDestacadas.asStateFlow()

    var _actividadesTodas = MutableStateFlow<List<Actividad>>(emptyList())
    val actividadesTodas = _actividadesTodas.asStateFlow()


    fun getActividadesDestacadas() {
        viewModelScope.launch(Dispatchers.IO) {
            val lista = actividadesRepository.getAllDestacadas()
            _actividadesDestacadas.value = lista
        }

    }

    fun getAllActividades() {
        viewModelScope.launch(Dispatchers.IO) {
            val lista = actividadesRepository.getActividadesNovedades() // esta no
            _actividadesTodas.value = lista
        }
    }

    fun cargarDatos() {
        viewModelScope.launch(Dispatchers.IO) {
            fireStoreModel.getActividadesRealtime(100).collect {listadoActividades ->
                db.actividadDao().insertAll(db.actividadDao().mapToActividadDB(listadoActividades, 0))
                _actividadesTodas.value =
                    db.actividadDao().mapToActividad(db.actividadDao().getAll())
            }
        }
    }

    fun cargarDestacadas() {
        viewModelScope.launch(Dispatchers.IO) {
            val listaPeticion: List<String> = listOf(
                "sCvtbUBSG6EE80RzRMYg",
                "3gFXlXwKzOg4Z6reHbOf",
                "937fEpccGoduIraSF48k",
                "MhzDNdqort8yIlsDvLqY",
                "Ue518MOgVtRF2PZr2eBp"
            )

            val listado = fireStoreModel.getDestacadas(listaPeticion)
            actividadesRepository.introducirActividadesDestacadas(listado)
        }

    }

    fun existeBaseDatos() {
        val dbFile = context.getDatabasePath("database-vivemurcia") // nombre de tu DB

        if (!dbFile.exists()) {
            cargarDatos()
            cargarDestacadas()
        }

    }


}


