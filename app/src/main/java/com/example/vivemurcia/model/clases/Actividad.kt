package com.example.vivemurcia.model.clases

import com.example.vivemurcia.model.enums.EnumAmbiente
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.enums.EnumGrupos
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date


val date = Date()
val specificTimestamp = Timestamp(date)

data class Actividad(
    @DocumentId val idActividad: Int? = null, // Assuming id can be null
    val imagenActividad: String? = "https://pixabay.com/es/photos/plantas-%C3%A1rboles-verdor-bosque-7705865/",
    val tituloActividad: String? = "Titulo por defecto",
    val fechaHoraActividad: Timestamp? = specificTimestamp,
    val ambienteActividad: EnumAmbiente?,
    val categoriaActividad: EnumCategories?,
    val descripcionActividad : String?,
    val localizacionActividad: String?,
    val tipoDeGrupo: EnumGrupos?,
    val ubicacionActividad: String?
)


