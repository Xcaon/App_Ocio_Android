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
        imageUri: Uri,
        tituloActividad: String
    ): Uri {

        var tituloSinEspacios = normalizarTitulo(tituloActividad)

        val imageRef =
            storage.reference.child("/users/$idEmpresa/$tituloSinEspacios/imagenActividad.jpg")


        val uploadTask = imageRef.putFile(imageUri)

        // Aprovechamos y recuperamos la url para asignarselo a la actividad
        val downloadurl = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnFailureListener(
            OnFailureListener {
                Log.e("fernando", "Error al subir la imagen")
            }
        ).addOnSuccessListener {
            Log.i("fernando", "La imagen se ha subido correctamente")
        }.await()

        return downloadurl
    }

    // Recuperamos una imagen
    suspend fun getImagen(tituloActividad: String?, idEmpresa: String?): Uri? {
        var uri: Uri? = null
        try {
            var tituloSinEspacios = normalizarTitulo(tituloActividad.toString())
            val imageRef =
                storage.reference.child("users/$idEmpresa/$tituloSinEspacios/imagenActividad.jpg")
            Log.i("fernando", "Buscando en $imageRef")
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



}