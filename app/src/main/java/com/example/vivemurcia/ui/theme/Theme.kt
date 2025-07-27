package com.example.vivemurcia.ui.theme

import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig.getThemeColor

private val DarkColorScheme = darkColorScheme(
    primary = colorPrimario,
    secondary = SoftPink,
    background = fondoNegro,
    onBackground = Color.White,
    surface = colorNegroProyecto,
    primaryContainer = Color(0xFFB388FF), // color del fondo del icono activo
    onPrimaryContainer = Color.Black      // color del icono dentro del fondo
)

private val LightColorSchemePersonalizado = lightColorScheme(
    primary = Color.White,
    secondary = Color.Black,
    background = fondoPantalla,
    onBackground = colorNegroProyecto,
    surface = Color.White,
    onSurface = colorNegroProyecto,
    primaryContainer = Color(0xFFEE0606),
    onPrimaryContainer = Color.Black
)

@Composable
fun VivemurciaTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorSchemePersonalizado

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


//@Composable
//fun VivemurciaTheme(
//    darkTheme: Boolean = getThemeColor(LocalContext.current),
//    // Dynamic color is available on Android 12+
////    dynamicColor: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    Log.d("darkTheme", "VivemurciaTheme: $darkTheme")
//    val colorScheme = when {
////        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
////            val context = LocalContext.current
////            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
////        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorSchemePersonalizado
//    }

