package com.example.vivemurcia.viewsUser.PreHomeCargador

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.example.vivemurcia.views.home.HomeView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreHomeCargadorViewModel @Inject constructor(
    val fireStoreModel: FireStoreModel,
    val db: AppDatabase
) : ViewModel() {

    // Hacer flow para saber cuando para de cargar
    var _cargando = MutableStateFlow<Boolean>(true)
    val cargando = _cargando.asStateFlow()


    // Añadimos de Firebase las actividades a Room
     fun cargarDatosApp() {

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("fernando", "Cargando valor ${_cargando.value}")

            // Recuperamos las actividades
            // Collect es suspend porque es un Flow, se esperara en este punto
            val listadoActividades = fireStoreModel.getAllActividades()
            Log.d("fernando", "Cargando valor $listadoActividades")
            // Insertamos en room
            Log.d("fernando", "Insertamos en Room")
            db.actividadDao().insertAll(db.actividadDao().mapToActividadDB(listadoActividades, 0))
            _cargando.value = false
            Log.d("fernando", "Cargando valor ${_cargando.value}")
        }

    }

}