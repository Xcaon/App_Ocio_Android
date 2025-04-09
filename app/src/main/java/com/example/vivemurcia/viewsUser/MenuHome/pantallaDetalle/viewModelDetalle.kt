package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.vivemurcia.model.clases.Actividad

class viewModelDetalle : ViewModel() {

    // Definimos el navController para coger el control de las pantallas
     var navController : NavController? = null
    // Cogemos la actividad que vamos a renderizar en la pantalla detalle
    var actividadSeleccionada : Actividad? = null

    // Hay que llamar siempre antes a esta funcion
    fun inicialNavController(navController: NavController) {
        if (this.navController == null) {
            this.navController = navController
        }
    }

    fun mostrarActividadDetalle(actividad: Actividad) {
        this.actividadSeleccionada = actividad

        val titulo: String = Uri.encode(actividad.tituloActividad)
        // Me sale que es null si no pongo el ?: "Juan carlos 1"
        val localizacion: String = Uri.encode(actividad.localizacionActividad) ?: "Juan carlos 1"
        Log.w("fernando", "Esta es la actividad del detalle $actividad")

        navController?.navigate("detalle/$titulo/$localizacion")
    }

}