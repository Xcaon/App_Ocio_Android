package com.example.vivemurcia.viewsUser.PreHomeCargador

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dotlottie.dlplayer.Mode
import com.example.vivemurcia.R
import com.example.vivemurcia.ui.theme.colorPrimario
import com.example.vivemurcia.ui.theme.textoNaranja
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource


@Composable
fun start(cargarhome: () -> Unit) {

    val preHomeCargadorViewModel: PreHomeCargadorViewModel =
        hiltViewModel<PreHomeCargadorViewModel>()
    val cargando: Boolean by preHomeCargadorViewModel.cargando.collectAsState()

    LaunchedEffect(Unit) {
        preHomeCargadorViewModel.cargarDatosApp()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cargando) {
            DotLottieAnimation(
                source = DotLottieSource.Url("https://lottie.host/6a188891-a2a3-4169-9cac-b12dd8ef678a/wT0mf5o3xM.lottie"), // from url .lottie / .json
                autoplay = true,
                loop = true,
                speed = 3f,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier.background(
                    Color.White
                )
            )
            Espaciado(12)
            Text(
                "Configurando Aplicación",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
                color = textoNaranja
            )
        } else {
            cargarhome()
        }
    }


}
