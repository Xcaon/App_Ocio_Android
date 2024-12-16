package com.example.vivemurcia.model.clases

import com.google.firebase.Timestamp
import java.util.Date


val date = Date()
val specificTimestamp = Timestamp(date)

data class Actividad(
    val id: Int? = 5, // Assuming id can be null
    val imagen: String? = "gwegwgwe",
    val titulo: String? = "Titulo por defecto",
    val horario: Timestamp? = specificTimestamp
)


