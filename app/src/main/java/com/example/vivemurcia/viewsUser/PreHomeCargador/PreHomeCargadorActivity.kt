package com.example.vivemurcia.viewsUser.PreHomeCargador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vivemurcia.views.home.HomeView
import com.example.vivemurcia.viewsCompany.ui.theme.VivemurciaTheme
import com.example.vivemurcia.viewsUser.actividadInfo.InfoView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java

@AndroidEntryPoint
class PreHomeCargadorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VivemurciaTheme {
                start() {
                    val intent = Intent(this@PreHomeCargadorActivity, HomeView::class.java)
                    startActivity(intent)
                    finish() // opcional, si no quieres volver atrás al splash/login
                }
            }
        }
    }
}

