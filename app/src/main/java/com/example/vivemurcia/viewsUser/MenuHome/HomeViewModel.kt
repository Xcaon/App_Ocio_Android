package com.example.vivemurcia.views.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    // MutableLiveData: Permite actualizar el valor de la lista de actividades
    private val _actividadesAventuras =
        MutableStateFlow<List<Actividad>>(getActividades(EnumCategories.AVENTURAS))
    private val _actividadesCocina =
        MutableStateFlow<List<Actividad>>(getActividades(EnumCategories.COCINA))
    private val _actividadesRelax =
        MutableStateFlow<List<Actividad>>(getActividades(EnumCategories.RELAX))
    private val _actividadesArte =
        MutableStateFlow<List<Actividad>>(getActividades(EnumCategories.ARTE))

    //LiveData: Permite que otros componentes observen los cambios en la lista de actividades sin tener acceso directo para modificarla
    val actividadesAventuras: StateFlow<List<Actividad>> = _actividadesAventuras.asStateFlow()
    val actividadesCocina: StateFlow<List<Actividad>> = _actividadesCocina.asStateFlow()
    val actividadesRelax: StateFlow<List<Actividad>> = _actividadesRelax.asStateFlow()
    val actividadesArte: StateFlow<List<Actividad>> = _actividadesArte.asStateFlow()

    // Lista original para conservar las actividades completas
    var actividadesOriginalesAventuras: List<Actividad> = emptyList()
    var actividadesOriginalesCocina: List<Actividad> = emptyList()
    var actividadesOriginalesRelax: List<Actividad> = emptyList()
    var actividadesOriginalesArte: List<Actividad> = emptyList()


    fun getActividades(categoria: EnumCategories): List<Actividad> {

        // Actividad vacia para rellenarla con la petición a Firestore
        var nuevasActividades: List<Actividad> = emptyList<Actividad>()
        viewModelScope.launch {
            try {
                // Obtenemos las actividades de Firestore
                nuevasActividades = fireStoreModel.getActividades(categoria)
//                Log.w("fernando", "Esta es la lista que recogemos de firebase direc $nuevasActividades")
                when (categoria) {
                    EnumCategories.AVENTURAS -> {
                        _actividadesAventuras.value = nuevasActividades
//                        Log.w("fernando", "Esta es la lista que recogemos de firebase $nuevasActividades")
                        actividadesOriginalesAventuras = nuevasActividades
                    }
                    EnumCategories.COCINA -> {
                        _actividadesCocina.value = nuevasActividades
                        actividadesOriginalesCocina = nuevasActividades
                    }
                    EnumCategories.RELAX -> {
                        _actividadesRelax.value = nuevasActividades
                        actividadesOriginalesRelax = nuevasActividades
                    }
                    EnumCategories.ARTE -> {
                        _actividadesArte.value = nuevasActividades
                        actividadesOriginalesArte = nuevasActividades
                    }
                }
            } catch (e: Exception) {
                Log.i(
                    "fernando",
                    "Error al obtener las actividades de aventuras 'HomeViewModel'" + e.message.toString()
                )
            }
        }
        return nuevasActividades
    }





    // PENDIENTE DE ADAPTAR CON TODOS LOS FILTROS
    // Actualizar actividades según el filtro
    fun filterActividades(query: String) {
        if (query.isBlank()) {
            _actividadesAventuras.value = actividadesOriginalesAventuras // Sin filtro
            _actividadesCocina.value = actividadesOriginalesCocina
            _actividadesRelax.value = actividadesOriginalesRelax
            _actividadesArte.value = actividadesOriginalesArte
        } else {
            _actividadesAventuras.value = actividadesOriginalesAventuras.filter { actividad ->
                actividad.tituloActividad!!.contains(query, ignoreCase = true)
            }
            _actividadesCocina.value = actividadesOriginalesCocina.filter { actividad ->
                actividad.tituloActividad!!.contains(query, ignoreCase = true)
            }
            _actividadesRelax.value = actividadesOriginalesRelax.filter { actividad ->
                actividad.tituloActividad!!.contains(query, ignoreCase = true)
            }
            _actividadesArte.value = actividadesOriginalesArte.filter { actividad ->
                actividad.tituloActividad!!.contains(query, ignoreCase = true)
            }
        }
    }

}


