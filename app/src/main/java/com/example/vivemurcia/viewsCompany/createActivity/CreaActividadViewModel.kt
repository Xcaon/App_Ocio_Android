package com.example.vivemurcia.viewsCompany.createActivity

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreaActividadViewModel @Inject constructor
    (private val storage: FireStorageModel, private val firestore: FireStoreModel) :
    ViewModel() {

    suspend fun crearActividad(actividad: Actividad, estadoSubido: (Boolean) -> Unit) {
        estadoSubido(firestore.subirActividad(actividad))
    }

    fun subirImagenUri(idEmpresa: String, uri: Uri, tituloActividad: String, estadoSubido: (Boolean) -> Unit) {
        // Subimos la imagen

        storage.subirImagen(
            //
            idEmpresa = idEmpresa,
            imageUri = uri,
            tituloActividad = tituloActividad
        ) {
            estadoSubido(it)
        }
    }


}