package com.example.vivemurcia.model.firebase

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStorageModel @Inject constructor(
    private val storage: FirebaseStorage
) {



    suspend fun subirImagen(
        idEmpresa: String,
        imageUri: ByteArray,
        tituloActividad: String
    ): Uri {

        val tituloSinEspacios = normalizarTitulo(tituloActividad)

        val imageRef =
            storage.reference.child("/users/$idEmpresa/$tituloSinEspacios/imagenActividad.jpg")


        val uploadTask = imageRef.putBytes(imageUri)

        val downloadUrl = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnSuccessListener { task ->
            // Subida completada
            imageRef.downloadUrl
        }.addOnFailureListener { exception ->
            // Manejar error
            Log.e("Upload", "Error al subir: $exception")
        }.await()

        return downloadUrl
    }

    // Recuperamos una imagen
    suspend fun getImagen(tituloActividad: String?, idEmpresa: String?): Uri? {
        var uri: Uri? = null
        try {
            var tituloSinEspacios = normalizarTitulo(tituloActividad.toString())
            val imageRef =
                storage.reference.child("users/$idEmpresa/$tituloSinEspacios/imagenActividad.jpg")
//            Log.i("fernando", "Buscando en $imageRef")
            uri = imageRef.downloadUrl.await()
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener la imagen: ${e.message}")
        }
        return uri
    }

    private fun normalizarTitulo(titulo: String): String {
        return titulo
            .trim() // elimina espacios al principio y final
            .replace("\\s+".toRegex(), "_") // reemplaza espacios internos por "_"
            .lowercase() // opcional: homogéneo en minúsculas
    }


    // Recuperamos una imagen
    suspend fun getUriIcono(nombreIcono: String?): Uri? {
        var uri: Uri? = null
        try {
            Log.i("tabs", "El nombre es $nombreIcono")
            val imageRef =
                storage.reference.child("/iconosCategorias/$nombreIcono.png")
//            Log.i("fernando", "Buscando en $imageRef")
            uri = imageRef.downloadUrl.await()
        } catch (e: Exception) {
            Log.e("fernando", "Error al obtener la uri de los iconos de tabs: ${e.message}")
        }
        return uri
    }


}