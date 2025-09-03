package com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Right
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.vivemurcia.R
import androidx.core.net.toUri
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.negroIconos
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.example.vivemurcia.viewsCompany.ui.theme.botonNaranja
import com.example.vivemurcia.viewsUser.MenuFavoritos.ViewModelFavoritos
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MostrarActividadDetalle(idActividad: String?, categoriaActividad: String?) {
    val context = LocalContext.current

    val viewModelDetalle: ViewModelDetalle = hiltViewModel<ViewModelDetalle>()

    val actividad = viewModelDetalle.actividad.collectAsState()
    val actividadValue: Actividad? = actividad.value


    val isFav by viewModelDetalle.isFav.collectAsState()


    LaunchedEffect(idActividad, categoriaActividad) {
        viewModelDetalle.PintarActividadDetalle(idActividad.toString(), categoriaActividad)
        viewModelDetalle.isFavorito(idActividad!!)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        // Imagen de fondo (en la parte superior)

        // Contenido que flota debajo de la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .weight(0.5f) // 👈 fondo para que no se vea transparente
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
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 48.dp)
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.notosansbold)),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                text = actividadValue?.tituloActividad?.replaceFirstChar { it.uppercase() } ?: ""
            )

//            Text(
//                text = "por ${actividadValue?.idEmpresa}",
//                style = TextStyle(textDecoration = TextDecoration.Underline)
//            )
            Spacer(modifier = Modifier.height(4.dp))
//            if (actividadValue?.fechaHoraActividad != null) {
//                Text(
//                    textAlign = Right,
//                    text = mostrarFechaBonita(actividadValue.fechaHoraActividad),
//                    style = TextStyle(textDecoration = TextDecoration.Underline)
//                )
//            }
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
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonColors(botonNaranja, Color.White, botonNaranja, botonNaranja),
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        actividadValue?.localizacionActividad?.toUri()
                    )
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Cómo llegar")
            }

            if (isFav) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Actividad borrada", Toast.LENGTH_SHORT).show()
                        viewModelDetalle.borrarDeFavoritos(actividadValue)
                    },
                    colors = ButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Row {
                        Text(text = "Borrar Favorito")
                        Espaciado(4)
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Ir",
                            tint = Color.White
                        )
                    }
                }
            } else {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Actividad guardada", Toast.LENGTH_SHORT).show()
                        viewModelDetalle.addActividadListaFavoritos(idActividad)
                    },
                    colors = ButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Row {
                        Text(text = "Guardar Favorito")
                        Espaciado(4)
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Ir",
                            tint = Color.White
                        )
                    }
                }
            }

        }
    }
}

fun mostrarFechaBonita(fecha: Timestamp): String {
    // Fecha y hora
    val timestamp: Timestamp = fecha // de Firebase
    val calendar = Calendar.getInstance().apply {
        time = timestamp.toDate()
    }
    val formato = SimpleDateFormat("d MMMM", Locale("es", "ES"))

    return formato.format(calendar.time)
}


