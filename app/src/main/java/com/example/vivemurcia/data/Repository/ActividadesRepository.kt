package com.example.vivemurcia.data.Repository

import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import javax.inject.Inject


class ActividadesRepository @Inject constructor(
    private val fireStoreModel: FireStoreModel,
    private val db: AppDatabase
) {

    fun getAllDestacadas(): List<Actividad> {
       val listado = db.actividadDao().mapToActividad(db.actividadDao().getAllDestacadas())
        return listado
    }


    suspend fun introducirActividadesDestacadas(listado: List<Actividad>) {
        db.actividadDao().insertAll(db.actividadDao().mapToActividadDB(listado, 1))
    }


    suspend fun getActividadesNovedades(): List<Actividad> {
        // Si ya existe, lo cogemos directamente
        val lista = db.actividadDao().mapToActividad(db.actividadDao().getAll())
        return lista
    }

}