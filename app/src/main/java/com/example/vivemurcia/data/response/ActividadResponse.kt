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

// Deben llamarse igual los nombre para que pueda funcionar al solicitarlo a firestore
data class ActividadResponse (
    var idActividad: String? = null,
    val tituloActividad: String? = null,
    val fechaHoraActividad: Timestamp? = null,
    val descripcionActividad: String? = null,
    val categoriaActividad: EnumCategories? = null,
    val ambienteActividad: EnumAmbiente? = null,
    val localizacionActividad: String? = null,
    val tipoDeGrupo: EnumGrupos? = null,
    val ubicacionActividad: String? = null,
    val idEmpresa: String? = null
)
{
    // Esta funcion lo convierte en un objeto de tipo Actividad, basicamente por si queremos cambiarle algun nombre a los atributos
     fun toDomain(): Actividad {
        return Actividad(
            idActividad = idActividad,
            tituloActividad = tituloActividad,
            fechaHoraActividad = fechaHoraActividad,
            ambienteActividad = ambienteActividad,
            categoriaActividad = categoriaActividad,
            descripcionActividad = descripcionActividad,
            localizacionActividad = localizacionActividad,
            tipoDeGrupo = tipoDeGrupo,
            ubicacionActividad = ubicacionActividad,
            idEmpresa = idEmpresa
        )
    }

}

