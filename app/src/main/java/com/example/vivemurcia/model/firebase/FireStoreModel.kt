package com.example.vivemurcia.model.firebase

import android.util.Log
import com.example.vivemurcia.data.response.ActividadResponse
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.clases.Categoria
import com.example.vivemurcia.model.enums.EnumCategories
import com.google.firebase.firestore.FieldValue
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

    suspend fun getSingleActivity(idActividad: String, categoriaActividad: String) : Actividad? {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES + categoriaActividad)
                .document(idActividad)
                .get(Source.DEFAULT)
                .await().toObject(ActividadResponse::class.java)!!.toDomain()
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener la actividad de Firestore: ${e.message}")
            null
        }
    }

    // Nos devuelve una lista de actividades
    suspend fun getActividades(categoria : EnumCategories): List<Actividad> {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES + categoria.nombre)
                .get(Source.DEFAULT) /* Lee los datos de la caché local si están disponibles y no han expirado. Si los datos no están en la caché o han expirado, se leerán del servidor. */
                .await()
                .map{ document ->
                    val response = document.toObject(ActividadResponse::class.java)
                    var actividad = response.toDomain()
                    actividad.idActividad = document.id

                    // Con async ponemos en segundo plano a recuperar la imagen
                    val imageUrlDeferred = CoroutineScope(Dispatchers.IO).async {
                        storage.getImagen(actividad.tituloActividad, actividad.idEmpresa)
                    }
                    // Aqui usamos await para decir oye cuando acabes de obtener en segundo plano la imagen me la asignas
                    actividad.apply { uriImagen = imageUrlDeferred.await() }
                }
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener las actividades de Firestore: ${e.message}")
            emptyList() // Return an empty list if cache is empty
        }
    }

    // Subir actividad
    suspend fun subirActividad(actividad: Actividad): Boolean {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES + actividad.categoriaActividad).add(actividad).await()
            true
        } catch (e: Exception) {
            Log.e("fernando", "Error al subir la actividad a Firestore: ${e.message}")
            false
        }
    }

    suspend fun getCategorias() : List<Categoria> {

        return try {
            firestore.collection("/categorias").get(Source.DEFAULT).await().map { document ->
                var response: Categoria = document.toObject(Categoria::class.java)
                var categoria: Categoria = response.toDomain()
                categoria.iconoUri = storage.getUriIcono(categoria.nombre).toString()
                categoria
            }
        } catch (e: Exception) {
            Log.e("firestore","No se pueden recoger las categorias + $e")
            emptyList()
        }
    }

    fun subirActividadListaFavoritos(idActividad: String?, userId: String?)  {
        val firestore = FirebaseFirestore.getInstance()
        val ref = firestore.collection("usuarios").document(userId.toString())

        ref.get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    // Documento no existe, lo creamos con el array
                    val datosIniciales = mapOf(
                        "actividadesFavoritasIds" to listOf(idActividad)
                    )

                    ref.set(datosIniciales)
                        .addOnSuccessListener {
                            Log.d("fernando", "Documento creado y favorito añadido")
                        }
                        .addOnFailureListener { e ->
                            Log.e("fernando", "Error al crear documento: ${e.message}")
                        }
                } else {
                    // Documento ya existe, solo hacemos update
                    ref.update("actividadesFavoritasIds", FieldValue.arrayUnion(idActividad))
                        .addOnSuccessListener {
                            Log.d("fernando", "Favorito añadido al documento existente")
                        }
                        .addOnFailureListener { e ->
                            Log.e("fernando", "Error al añadir favorito: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("fernando", "Error al comprobar existencia del documento: ${e.message}")
            }

    }


}




