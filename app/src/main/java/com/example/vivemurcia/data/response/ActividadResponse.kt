package com.example.vivemurcia.data.response

import com.example.vivemurcia.model.clases.Actividad
import com.google.firebase.Timestamp

data class ActividadResponse(
    val idActividad: Int? = null,
    val ImagenActividad: String? = null,
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
            id = idActividad,
            imagen = ImagenActividad,
            titulo = tituloActividad,
            horario = fechaHoraActividad,
        )
    }
}

