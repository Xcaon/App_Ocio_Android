package com.example.vivemurcia.model.sharedPreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class PreferencesConfig {

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_INFO_VISTO = "infoVisto"

        fun preferenceSwitchValue(valor : Boolean, context : Context){
            // Vamos a un sitio u a otro depende si ha visto la pantalla de info
            val sharedPreferences = context.getSharedPreferences(
                PREF_NAME,
                MODE_PRIVATE
            )

            sharedPreferences.edit() {
                putBoolean(KEY_INFO_VISTO, valor)
            }
        }

        fun comprobarInfoVisto( context : Context) : Boolean {
            // Vamos a un sitio u a otro depende si ha visto la pantalla de info
            val sharedPreferences = context.getSharedPreferences(
                PREF_NAME,
                MODE_PRIVATE
            )
            // False es el valor por defecto si no existe la clave
            return sharedPreferences.getBoolean(KEY_INFO_VISTO,  false)
        }
    }
}