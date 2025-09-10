package com.example.vivemurcia.data.room

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vivemurcia.model.clases.Actividad
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface ActividadDAO {

    @Query("SELECT * FROM ActividadDB where idActividad = :idActividad LIMIT 1")
    fun getActividadById(idActividad: String): ActividadDB?

    @Query("SELECT * FROM ActividadDB where esFavorito = 1")
    fun getFavoritos(): List<ActividadDB>

    @Query("Select esFavorito FROM ActividadDB where idActividad = :idActividad")
    fun isFav(idActividad: String): Int

    // Hacemos uso de ':' para referirnos al parametro recibido por  funcion
    @Query("UPDATE ActividadDB SET esFavorito = :isFavValue WHERE idActividad = :idActividad")
    fun setDeleteFav(idActividad: String, isFavValue: Int)

    // Este es para el home
    @Query("SELECT * FROM ActividadDB LIMIT 12")
    fun getAll(): List<ActividadDB>

    // Para la pantalla de busquedas
    @Query("SELECT * FROM ActividadDB")
    fun getAllBusqueda(): List<ActividadDB>

    @Query("SELECT * FROM ActividadDB WHERE esDestacada = 1")
    fun getAllDestacadas(): List<ActividadDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(actividades: List<ActividadDB>)

    @Query("UPDATE ActividadDB SET esDestacada = 1 WHERE idActividad IN (:listadoIds)")
    fun insertAllDestacadas(listadoIds: List<String>)


    @Query("DELETE FROM ActividadDB")
    fun deleteAll()

    fun mapToActividadDB(listadoActividades: List<Actividad>, esDestacada: Int): List<ActividadDB> {

        val actividadDB: List<ActividadDB> = listadoActividades.map {
            ActividadDB(
                idActividad = it.idActividad!!,
                idEmpresa = it.idEmpresa,
                tituloActividad = it.tituloActividad,
                fechaHoraActividad = it.fechaHoraActividad?.toDate()?.time,
                ambienteActividad = it.ambienteActividad,
                categoriaActividad = it.categoriaActividad,
                descripcionActividad = it.descripcionActividad,
                localizacionActividad = it.localizacionActividad,
                tipoDeGrupo = it.tipoDeGrupo,
                ubicacionActividad = it.ubicacionActividad,
                uriImagen = it.uriImagen.toString(),
                esDestacada = esDestacada,
                esFavorito = 0
            )
        }
        return actividadDB
    }

    fun mapToActividad(listadoActividades: List<ActividadDB>): List<Actividad> {
        val actividades: List<Actividad> = listadoActividades.map {
            Actividad(
                idActividad = it.idActividad,
                idEmpresa = it.idEmpresa,
                tituloActividad = it.tituloActividad,
                fechaHoraActividad = Timestamp(Date(it.fechaHoraActividad!!)),
                ambienteActividad = it.ambienteActividad,
                categoriaActividad = it.categoriaActividad,
                descripcionActividad = it.descripcionActividad,
                localizacionActividad = it.localizacionActividad,
                tipoDeGrupo = it.tipoDeGrupo,
                ubicacionActividad = it.ubicacionActividad,
                uriImagen = it.uriImagen?.toUri()
            )
        }

        return actividades
    }




//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User


}