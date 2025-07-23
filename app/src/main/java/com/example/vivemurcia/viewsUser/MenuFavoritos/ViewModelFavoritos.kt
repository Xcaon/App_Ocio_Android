package com.example.vivemurcia.viewsUser.MenuFavoritos

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFavoritos @Inject constructor(val firestore: FireStoreModel, @ApplicationContext val context: Context) : ViewModel()
{

    var _favoritoUsuario = MutableStateFlow<List<Actividad>>(emptyList())
    var favorito : StateFlow<List<Actividad>> = _favoritoUsuario.asStateFlow()

     fun conseguirFavoritosDelUsuario(){
         viewModelScope.launch {
             val uidUsuario: String? = PreferencesConfig.getUserId(context)
             val favoritos: List<Actividad> = firestore.conseguirFavoritosDelUsuario(uidUsuario)

             _favoritoUsuario.value = favoritos
         }
    }

}