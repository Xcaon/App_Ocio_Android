package com.example.vivemurcia.viewsCompany.createActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vivemurcia.viewsCompany.ui.theme.VivemurciaTheme

class CrearActividad : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VivemurciaTheme {
                InicioCrearActividad()
            }
        }
    }
}

