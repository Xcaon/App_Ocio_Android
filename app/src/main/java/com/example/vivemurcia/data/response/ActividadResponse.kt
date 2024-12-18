package com.example.vivemurcia.data.response

import com.example.vivemurcia.model.clases.Actividad
import com.google.firebase.Timestamp

data class ActividadResponse(
    val idActividad: Int? = null,
    val imagenActividad: String? = null,
    val tituloActividad: String? = null,
    val fechaHoraActividad: Timestamp? = null,
    val descripcionActividad: String? = null,
    val categoriaActividad: String? = null,
    val ambiente: String? = null,
    val localizacionGoogleMaps: String? = null,
    val tiposdeGrupos: String? = null,
    val ubicacionActividad: String? = null,
)
{
    fun toDomain(): Actividad {
        return Actividad(
            idActividad = idActividad,
            imagenActividad = imagenActividad,
            tituloActividad = tituloActividad,
            fechaHoraActividad = null,
            ambienteActividad = null,
            categoriaActividad = null,
            descripcionActividad = null,
            localizacionActividad = null,
            tipoDeGrupo = null,
            ubicacionActividad = null,
        )
    }
}

