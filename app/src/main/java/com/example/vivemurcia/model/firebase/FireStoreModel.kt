package com.example.vivemurcia.model.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.vivemurcia.data.response.ActividadResponse
import com.example.vivemurcia.model.clases.Actividad
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get


class FireStoreModel @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    companion object {
        const val COLLECTION_ACTIVIDADES = "actividades"
    }

    // Nos devuelve una lista de actividades
    suspend fun getAveturasCollection(): List<Actividad> {

        return try {
            firestore.collection(COLLECTION_ACTIVIDADES)
                .get(Source.CACHE) // Use Source.CACHE to retrieve from cache
                .await()
                .map { actividad -> actividad.toObject(ActividadResponse::class.java).toDomain() }
        } catch (e: Exception) {
            // Handle cache miss (e.g., retrieve from server)
            Log.i("fernando", "Error al obtener las actividades de Firestore: ${e.message}")
            emptyList() // Return an empty list if cache is empty
        }

    }


}




