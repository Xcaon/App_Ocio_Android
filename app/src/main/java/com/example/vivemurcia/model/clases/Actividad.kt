package com.example.vivemurcia.model.clases

import android.net.Uri
import com.example.vivemurcia.model.enums.EnumAmbiente
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.enums.EnumGrupos
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date


val date = Date()
val specificTimestamp = Timestamp(date)

data class Actividad(
    @DocumentId var idActividad: String? = null, // Assuming id can be null
    val idEmpresa: String?,
    var tituloActividad: String? = "Titulo por defecto",
    val fechaHoraActividad: Timestamp? = specificTimestamp,
    val ambienteActividad: EnumAmbiente?,
    val categoriaActividad: EnumCategories?,
    val descripcionActividad : String?,
    val localizacionActividad: String?,
    val tipoDeGrupo: EnumGrupos?,
    val ubicacionActividad: String?,
    var uriImagen: Uri? = null
)


