package com.example.vivemurcia.viewsUser.actividadInfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vivemurcia.views.home.HomeView
import com.example.vivemurcia.views.info.Textoinicial
import com.example.vivemurcia.views.info.ui.theme.VivemurciaTheme

class InfoView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            VivemurciaTheme {
                // Pendiente de arreglar que se le da click al card y no al boton para ir al
                // HomeView
                Textoinicial {
                    var intent = Intent(this, HomeView::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}

