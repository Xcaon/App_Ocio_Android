package com.example.vivemurcia.model.firebase

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FireStorageModel @Inject constructor(
private val storage : FirebaseStorage)
{

    fun subirImagen(idEmpresa: String, imageUri: Uri, tituloActividad : String) {

        val imageRef = storage.reference.child("users/$idEmpresa/$tituloActividad/imagenActividad.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnFailureListener(
            OnFailureListener {
                Log.e("fernando", "Error al subir la imagen")
            }
        ).addOnSuccessListener {
            Log.i("fernando", "La imagen se ha subido correctamente")
        }

    }

}