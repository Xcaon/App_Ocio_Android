package com.example.vivemurcia.ui.theme

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig.getThemeColor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor( @ApplicationContext private val context: Context) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme


    fun getTheme() {
        Log.d("darkTheme", "getThemeValor: ${getThemeColor(context)}")
        _isDarkTheme.value = getThemeColor(context)
    }

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }


}