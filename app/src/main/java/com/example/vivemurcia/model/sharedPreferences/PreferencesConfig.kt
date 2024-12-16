package com.example.vivemurcia.model.sharedPreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PreferencesConfig {

    companion object {
        fun preferenceSwitchValue(valor : Boolean, context : Context){
            // Vamos a un sitio u a otro depende si ha visto la pantalla de info
            val sharedPreferences = context.getSharedPreferences(
                "app_preferences",
                MODE_PRIVATE
            )

            val editor = sharedPreferences.edit()
            editor.putBoolean("infoVisto", valor)
            editor.apply()
        }

        fun comprobarInfoVisto(valor: Boolean, context : Context) : Boolean{
            // Vamos a un sitio u a otro depende si ha visto la pantalla de info
            val sharedPreferences = context.getSharedPreferences(
                "app_preferences",
                MODE_PRIVATE
            )

            return sharedPreferences.getBoolean( "infoVisto" ,  false)
        }
    }
}