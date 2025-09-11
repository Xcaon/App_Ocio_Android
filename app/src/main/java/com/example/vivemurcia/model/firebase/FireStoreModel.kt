package com.example.vivemurcia.model.firebase

import android.net.Uri
import android.util.Log
import com.example.vivemurcia.data.response.ActividadResponse
import com.example.vivemurcia.data.room.ActividadDB
import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.clases.Categoria
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FireStoreModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FireStorageModel
) {

    companion object {
        const val COLLECTION_ACTIVIDADES = "actividades"
    }

    suspend fun getSingleActivity(idActividad: String, categoriaActividad: String): Actividad? {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES)
                .document(idActividad)
                .get(Source.DEFAULT)
                .await().toObject(ActividadResponse::class.java)!!.toDomain()
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener la actividad de Firestore: ${e.message}")
            null
        }
    }

    // Nos devuelve una lista de actividades destacadas que le definimos
    suspend fun getDestacadas(listaDestacadas: List<String>): List<Actividad> {

        val documentos = mutableListOf<Actividad>()

        for (idActividad in listaDestacadas) {

            val document = firestore.collection(COLLECTION_ACTIVIDADES)
                .document(idActividad)
                .get(Source.SERVER)
                .await()

            val response = document.toObject(ActividadResponse::class.java) ?: continue
            val actividad = response.toDomain()
            actividad.idActividad = document.id

            // Recuperar imagen en segundo plano
            val uri = withContext(Dispatchers.IO) {
                storage.getImagen(actividad.tituloActividad, actividad.idEmpresa)
            }
            actividad.uriImagen = uri

            documentos.add(actividad)
        }
        Log.d("HomeViewModel", "getActividadesDestacadasModel: $documentos")
        return documentos

    }

    // Esta funciona nos devuelve todas las actividades de firebase, para cargarlo en room
    suspend fun getAllActividades() : List<Actividad> {

        return try {
            val snapshot = firestore
                .collection(COLLECTION_ACTIVIDADES)
                .orderBy("fechaHoraActividad", Query.Direction.DESCENDING)
                .get()
                .await() // espera hasta que llegue el snapshot

            val listadoActividades = snapshot.documents.map { doc ->
                val response = doc.toObject(ActividadResponse::class.java)!!
                val actividad = response.toDomain().apply { idActividad = doc.id }

                // Obtener la imagen de storage
                val imagenUri = storage.getImagen(
                    actividad.tituloActividad,
                    actividad.idEmpresa
                )
                actividad.apply { uriImagen = imagenUri }
            }
            listadoActividades
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // devolvemos lista vacía en caso de error
        }

    }

    // Subir actividad
    suspend fun subirActividad(actividad: Actividad): Boolean {
        return try {
            firestore.collection("actividades")
                .add(actividad).await()
            true
        } catch (e: Exception) {
            Log.e("fernando", "Error al subir la actividad a Firestore: ${e.message}")
            false
        }
    }

    suspend fun getCategorias(): List<Categoria> {

        return try {
            firestore.collection("/categorias").get(Source.DEFAULT).await()
                .map { document: QueryDocumentSnapshot ->
                    var response: Categoria = document.toObject(Categoria::class.java)
                    var categoria: Categoria = response.toDomain()
                    categoria.iconoUri = storage.getUriIcono(categoria.nombre).toString()
                    categoria
                }
        } catch (e: Exception) {
            Log.e("firestore", "No se pueden recoger las categorias + $e")
            emptyList()
        }
    }

    // Subimos la actividad a la subcoleccion de actividades favoritas del usuario
    suspend fun subirActividadListaFavoritos(
        idActividad: String?,
        userId: String?,
        categoriaActividad: String?
    ) {
        // Documento del usuario en concreto
        var actividad: Actividad? =
            getSingleActivity(idActividad.toString(), categoriaActividad.toString())

        try {
            firestore.collection("usuarios")
                .document(userId.toString())
                .collection("favoritos")
                .document(idActividad.toString())
                .set(actividad!!)
                .addOnSuccessListener {
                    Log.d("Firestore", "Favorito guardado correctamente")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error al guardar el favorito", e)
                }
        } catch (e: Exception) {
            Log.e("Firestore", "Error al guardar el favorito", e)
        }
    }

    // Recuperar lista de favoritas de un usuario
    suspend fun conseguirFavoritosDelUsuario(userId: String?): List<Actividad> {
        return try {
            firestore.collection("usuarios").document(userId.toString()).collection("favoritos")
                .get().await().map { document ->
                    val response = document.toObject(ActividadResponse::class.java)
                    var actividad = response.toDomain()
                    actividad.apply { actividad.idActividad = document.id }
                }
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener las actividades de Firestore: ${e.message}")
            emptyList() // Return an empty list if cache is empty
        }
    }


}




