package com.example.vivemurcia.viewsCompany.createActivity

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.model.firebase.FireStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreaActividadViewModel @Inject constructor
    (private val storage: FireStorageModel, private val firestore: FireStoreModel) :
    ViewModel() {

    suspend fun crearActividad(actividad: Actividad, estadoSubido: (Boolean) -> Unit) {
        estadoSubido(firestore.subirActividad(actividad))
    }

    suspend fun subirImagenUri(idEmpresa: String, uri: ByteArray, tituloActividad: String): Uri {

        val uriImagen: Uri = storage.subirImagen(
            idEmpresa = idEmpresa,
            imageUri = uri,
            tituloActividad = tituloActividad
        )
        return uriImagen
    }


}