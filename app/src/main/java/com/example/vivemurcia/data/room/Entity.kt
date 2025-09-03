package com.example.vivemurcia.data.room

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vivemurcia.model.enums.EnumAmbiente
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.enums.EnumGrupos
import com.google.firebase.Timestamp
import org.jetbrains.annotations.NotNull

// Aqui definimos el objeto que se va a guardar en la base de datos
// En este caso las actividades
@Entity
data class ActividadDB(
    @PrimaryKey val idActividad: String,
    val idEmpresa: String?,
    val tituloActividad: String?,
    val fechaHoraActividad: Long?,
    val ambienteActividad: EnumAmbiente?,
    val categoriaActividad: EnumCategories?,
    val descripcionActividad: String?,
    val localizacionActividad: String?,
    val tipoDeGrupo: EnumGrupos?,
    val ubicacionActividad: String?,
    val uriImagen: String?,
    val esDestacada: Int, // Si es 0 no es destacada, si es 1 es destacada,
    val esFavorito: Int, // 0 si no es favorito, 1 si es favorito
)

