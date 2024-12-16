package com.example.vivemurcia.views.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.views.bottomBar.MyApp
import com.example.vivemurcia.views.bottomBar.Rutas
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VivemurciaTheme {
                MyApp()
            }
        }
    }
}