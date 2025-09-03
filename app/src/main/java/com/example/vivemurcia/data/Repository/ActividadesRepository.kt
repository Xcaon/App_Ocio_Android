package com.example.vivemurcia.data.Repository

import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import javax.inject.Inject


class ActividadesRepository @Inject constructor(
    private val fireStoreModel: FireStoreModel,
    private val db: AppDatabase
) {

    fun getFavoritos() : List<Actividad> {
        return db.actividadDao().mapToActividad(db.actividadDao().getFavoritos())
    }

    fun isFav(idActividad: String) : Boolean {
        val valor = db.actividadDao().isFav(idActividad)

        val condicion: Boolean = valor == 1

        return condicion
    }

    fun setFavorito(idActividad: String?, isFavValue: Int){
        db.actividadDao().setDeleteFav(idActividad!!, isFavValue)
    }

    fun getActividadesBusquedas(): List<Actividad> {
        val listado = db.actividadDao().mapToActividad(db.actividadDao().getAllBusqueda())
        return listado
    }

    fun getAllDestacadas(): List<Actividad> {
       val listado = db.actividadDao().mapToActividad(db.actividadDao().getAllDestacadas())
        return listado
    }

    fun introducirActividades(listado : List<Actividad>) {
        db.actividadDao().insertAll(db.actividadDao().mapToActividadDB(listado, 1))
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