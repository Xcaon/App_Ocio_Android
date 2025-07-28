package com.example.vivemurcia.model.firebase

import android.net.Uri
import android.util.Log
import com.example.vivemurcia.data.response.ActividadResponse
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.clases.Categoria
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FireStoreModel @Inject constructor(
    private val firestore: FirebaseFirestore, private val storage: FireStorageModel
) {

    companion object {
        const val COLLECTION_ACTIVIDADES = "actividades"
    }

    suspend fun getSingleActivity(idActividad: String, categoriaActividad: String): Actividad? {
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

    // Nos devuelve una lista de actividades destacadas que le definimos
    suspend fun getDestacadas(listaDestacadas: List<String>): List<Actividad> {

        val documentos = mutableListOf<Actividad>()

        for (idActividad in listaDestacadas) {

            val document = firestore.collection(COLLECTION_ACTIVIDADES)
                .document(idActividad)
                .get(Source.CACHE).addOnFailureListener {
                    firestore.collection(COLLECTION_ACTIVIDADES)
                        .document(idActividad).get(Source.SERVER)
                }
                .await()

            val response = document.toObject(ActividadResponse::class.java) ?: continue
            var actividad = response.toDomain()
            actividad.idActividad = document.id

            // Recuperar imagen en segundo plano
            val uri = withContext(Dispatchers.IO) {
                storage.getImagen(actividad.tituloActividad, actividad.idEmpresa)
            }
            actividad.uriImagen = uri

            documentos.add(actividad)
        }

        return documentos

    }


    // Nos devuelve todas las actividades
    suspend fun getAllActividades(limit: Long): List<Actividad> {

        val query = firestore.collection(COLLECTION_ACTIVIDADES)
            .orderBy("fechaHoraActividad", Query.Direction.DESCENDING)
            .limit(limit)

        val snapshot = try {
            query.get(Source.CACHE).await()
        } catch (e: Exception) {
            query.get(Source.SERVER).await()
        }

        val actividades = snapshot.documents.map { doc ->
            val response = doc.toObject(ActividadResponse::class.java)!!
            response.toDomain().apply { idActividad = doc.id }
        }

        // Lanzamos la descarga de imágenes en paralelo
        val actividadesConImagenes = coroutineScope {
            actividades.map { actividad ->
                async {
                    val imagenUri = storage.getImagen(actividad.tituloActividad, actividad.idEmpresa)
                    actividad.apply { uriImagen = imagenUri }
                }
            }.awaitAll() // Aquí esperamos todas y devolvemos la lista ya modificada
        }

        return actividadesConImagenes
    }

    // Subir actividad
    suspend fun subirActividad(actividad: Actividad): Boolean {
        return try {
            firestore.collection(COLLECTION_ACTIVIDADES + actividad.categoriaActividad)
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


    fun borrarDeFavoritos(actividadValue: Actividad?, uidUsuario: String?) {
        try {
            firestore.collection("usuarios").document(uidUsuario.toString())
                .collection("favoritos").document(actividadValue?.idActividad.toString())
                .delete()
                .addOnSuccessListener {
                    Log.d("Firestore", "Favorito borrado correctamente")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error al borrar el favorito", e)
                }
        } catch (e: Exception) {
            Log.e("Firestore", "Error al borrar el favorito", e)
        }
    }

    suspend fun isFavorito(actividadId: String, uidUsuario: String?): Boolean {
        Log.d("fernando", "isFavoritoIDACtividad: $actividadId")
        Log.d("fernando", "isFavoritoUID: $uidUsuario")
        return try {
            val document = firestore.collection("usuarios")
                .document(uidUsuario.toString())
                .collection("favoritos")
                .document(actividadId)
                .get()
                .await() // Espera real
            Log.d("fernando", "isFavorito: ${document.exists()}")
            document.exists()
        } catch (e: Exception) {
            Log.e("Firestore", "Error comprobando favorito", e)
            false
        }
    }


}




