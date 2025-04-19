package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.core.net.toUri
import com.example.vivemurcia.model.firebase.FireStorageModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MostrarActividadDetalle(idActividad: String?, categoriaActividad: String?)  {
    val context = LocalContext.current
    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()
    val actividad = viewModelDetalle.actividad.collectAsState()
    var actividadValue = actividad.value
    val scrollState = rememberScrollState()


    LaunchedEffect(idActividad, categoriaActividad) {
        viewModelDetalle.PintarActividadDetalle(idActividad.toString(), categoriaActividad)
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White)){

        // Imagen de fondo (en la parte superior)

        // Contenido que flota debajo de la imagen
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White).weight(0.5f) // 👈 fondo para que no se vea transparente
        ) {
            actividadValue?.uriImagen?.let { uri ->
                AsyncImage(
                    model = actividadValue.uriImagen,
                    contentDescription = "Fondo",
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = painterResource(id = R.drawable.fondologin),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Column(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 48.dp).verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.notosansbold)),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                text = actividadValue?.tituloActividad?.replaceFirstChar { it.uppercase() } ?: ""
            )
            Text(
                text = "por ${actividadValue?.idEmpresa}",
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = FontFamily(Font(R.font.notosansregular)),
                text = actividadValue?.descripcionActividad ?: ""
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Botones fijos abajo, superpuestos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW,
                        actividadValue?.localizacionActividad?.toUri()
                    )
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Cómo llegar")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { /* Guardar acción */ }
            ) {
                Text(text = "Guardar")
            }
        }
    }




}


