package com.example.vivemurcia.model.sharedPreferences

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.preferencesDataStore

object PreferencesConfig {

    private const val PREF_NAME = "user_preferences"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_INFO_VISTO = "info_visto"
    private const val KEY_THEME_COLOR = "theme_color"

    fun savePreferences(context: Context, userId: String?, infoVisto: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            if (userId != null) putString(KEY_USER_ID, userId)
            putBoolean(KEY_INFO_VISTO, infoVisto)
            apply()
        }

    }

    fun saveThemeColor(context: Context, isDarkTheme: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_THEME_COLOR, isDarkTheme)
        }
    }

    fun getThemeColor(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_THEME_COLOR, false)
    }

    fun getUserId(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_ID, null)
    }

    fun getInfoVisto(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_INFO_VISTO, false)
    }

    fun clearUserId(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    fun clearAll(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}
