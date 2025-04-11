package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.example.vivemurcia.views.bottomBar.Rutas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDetalle @Inject constructor(
    private val fireStoreModel: FireStoreModel
) : ViewModel() {

    var _actividad = MutableStateFlow<Actividad?>(null)
    // La que se expone al exterior para leer
    val actividad: StateFlow<Actividad?> = _actividad

    // Definimos el navController para coger el control de las pantallas
     var navController : NavController? = null


    // Hay que llamar siempre antes a esta funcion
    fun inicialNavController(navController: NavController) {
        if (this.navController == null) {
            this.navController = navController
        }
    }

    fun mostrarActividadDetalle(actividad: Actividad) {
            val idActividad : String = Uri.encode(actividad.idActividad)
            val categoriaActividad : String = Uri.encode(actividad.categoriaActividad.toString())
            navController?.navigate(Rutas.DETALLE.crearRuta(idActividad, categoriaActividad))

    }

    fun PintarActividadDetalle(idActividad: String, categoriaActividad: String?)
    {
        viewModelScope.launch {
            var actividad = fireStoreModel.getSingleActivity(idActividad, categoriaActividad.toString())
//            _actividad.value?.idActividad = idActividad
            _actividad.value = actividad
            _actividad.value?.idActividad = idActividad
            Log.d("fernando", "Id actividad en el launch del Detalle: $idActividad")
        }

    }

}