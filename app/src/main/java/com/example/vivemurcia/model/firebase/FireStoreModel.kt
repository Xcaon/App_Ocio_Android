package com.example.vivemurcia.model.firebase

import android.util.Log
import com.example.vivemurcia.data.response.ActividadResponse
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumCategories
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireStoreModel @Inject constructor(
    private val firestore: FirebaseFirestore, private val storage: FireStorageModel
) {

    companion object {
        const val COLLECTION_ACTIVIDADES = "actividades"
    }

    // Nos devuelve una lista de actividades
    suspend fun getActividades(categoria : EnumCategories): List<Actividad> {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES + categoria.nombre)
                .get(Source.DEFAULT) /* Lee los datos de la caché local si están disponibles y no han expirado. Si los datos no están en la caché o han expirado, se leerán del servidor. */
                .await().map { actividad ->
                    actividad.toObject(ActividadResponse::class.java)
                }.map { response: ActividadResponse ->
                    var actividad: Actividad = response.toDomain()
                    val imageUrlDeferred = CoroutineScope(Dispatchers.IO).async {
                        storage.getImagen(actividad.tituloActividad, actividad.idEmpresa)
                    }
                    actividad.apply { uriImagen = imageUrlDeferred.await() }
                }
        } catch (e: Exception) {
            // Handle cache miss
//            Log.wtf("fernando", "Error al obtener las actividades de Firestore: ${e.message}")
            emptyList() // Return an empty list if cache is empty
        }
    }

    // Subir actividad
    suspend fun subirActividad(actividad: Actividad): Boolean {
        return try {
//            Log.d("fernando", "A esta categoría va: ${COLLECTION_ACTIVIDADES + "Relax"}")
            firestore.collection(COLLECTION_ACTIVIDADES + actividad.categoriaActividad).add(actividad).await()
            true
        } catch (e: Exception) {
            Log.e("fernando", "Error al subir la actividad a Firestore: ${e.message}")
            false
        }
    }


}




