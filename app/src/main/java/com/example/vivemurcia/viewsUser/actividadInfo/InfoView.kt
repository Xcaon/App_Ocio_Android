package com.example.vivemurcia.viewsUser.actividadInfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig.savePreferences
import com.example.vivemurcia.ui.theme.ThemeViewModel
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.views.bottomBar.MyApp
import com.example.vivemurcia.views.home.HomeView
import com.example.vivemurcia.views.info.InicioComposableInfoView
import com.example.vivemurcia.viewsUser.PreHomeCargador.PreHomeCargadorActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InicioComposableInfoView {
                val intent = Intent(this, PreHomeCargadorActivity::class.java)
                startActivity(intent)
            }
        }
    }



}

