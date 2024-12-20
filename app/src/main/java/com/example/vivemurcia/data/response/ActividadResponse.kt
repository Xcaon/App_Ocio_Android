package com.example.vivemurcia.data.response

import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumAmbiente
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.enums.EnumGrupos
import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.views.home.HomeViewModel
import com.google.firebase.Timestamp


data class ActividadResponse (
    val idActividad: String? = null,
    val tituloActividad: String? = null,
    val fechaHoraActividad: Timestamp? = null,
    val descripcionActividad: String? = null,
    val categoriaActividad: EnumCategories? = null,
    val ambiente: EnumAmbiente? = null,
    val localizacionGoogleMaps: String? = null,
    val tiposdeGrupos: EnumGrupos? = null,
    val ubicacionActividad: String? = null,
    val idEmpresa: String? = null
)
{
    // La funcion que usamos para cargar el listado de actividades en el Home
     fun toDomain(): Actividad {
        return Actividad(
            idActividad = idActividad,
            tituloActividad = tituloActividad,
            fechaHoraActividad = fechaHoraActividad,
            ambienteActividad = ambiente,
            categoriaActividad = categoriaActividad,
            descripcionActividad = descripcionActividad,
            localizacionActividad = localizacionGoogleMaps,
            tipoDeGrupo = tiposdeGrupos,
            ubicacionActividad = ubicacionActividad,
            idEmpresa = idEmpresa
        )
    }

}

