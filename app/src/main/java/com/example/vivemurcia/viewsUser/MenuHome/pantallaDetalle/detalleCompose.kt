package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vivemurcia.R
import javax.inject.Inject


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MostrarActividadDetalle(idActividad: String?, categoriaActividad: String?)  {

    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()
    val actividad = viewModelDetalle.actividad.collectAsState()
    var actividadValue = actividad.value
    val scrollState = rememberScrollState()


    LaunchedEffect(idActividad, categoriaActividad) {
        viewModelDetalle.PintarActividadDetalle(idActividad.toString(), categoriaActividad)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Imagen de fondo
        AsyncImage(
            model = actividadValue?.uriImagen,
            contentDescription = "SuperHero Avatar",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp).background(Color.Black))

        // Contenido que flota sobre la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 280.dp) // 👈 Aquí ajustas cuánto flota el contenido sobre la imagen
                .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    fontFamily = FontFamily(Font(R.font.notosansbold)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    text = "${actividadValue?.tituloActividad}"
                )
                Text(
                    text = "por ${actividadValue?.idEmpresa}",
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    fontFamily = FontFamily(Font(R.font.notosansregular)),
                    text = "${actividadValue?.descripcionActividad}"
                )
                Button(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Cómo llegar")
                }
            }
        }
    }

}


