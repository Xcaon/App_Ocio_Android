package com.example.vivemurcia.views.home


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.views.bottomBar.MyApp
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter


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

// Como se pintara cada actividad, es decir, cada card
@Composable
fun ActividadCard(actividad: Actividad, onClickActividad: (Actividad) -> Unit) {

    // oye, Compose, prepara esta imagen desde una URL (o recurso local) y dame el estado para que yo lo gestione
    val painter = rememberAsyncImagePainter(model = actividad.uriImagen)
    // Miramos el estado actual de la imagen
    val imageState = painter.state
    // Cambiamos la variable de valor si el estado de la imagen ya esta subido
    val cargando : Boolean = imageState is AsyncImagePainter.State.Loading

    // Fecha y hora
    val timestamp: Timestamp = actividad.fechaHoraActividad!! // de Firebase
    val calendar = Calendar.getInstance().apply {
        time = timestamp.toDate()
    }
    val formato = SimpleDateFormat("d MMMM", Locale("es", "ES"))
    val fechaBonita = formato.format(calendar.time)

    Card(
        border = BorderStroke(0.2.dp, fondoPantalla),
        modifier = Modifier
            .width(200.dp)
            .padding(4.dp).background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            // Aqui  lanzamos el proceso para enseñar la actividad
            onClickActividad(actividad)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background( if (cargando) shimmerBrush()  else SolidColor(Color.Transparent) )
        ) {
            AsyncImage(
                model = actividad.uriImagen,
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
                )
        }

        // Datos: Titulo y fecha de la actividad
        Column(
            modifier = Modifier.padding(
                8.dp, 8.dp, 8.dp, 2.dp
            )
        ) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    text = actividad.tituloActividad!!.replaceFirstChar { it.uppercase() },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(2.dp))
                Text(fontSize = 14.sp, text = "$fechaBonita")
                Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

@Composable
fun shimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnim"
    ).value

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 200f, translateAnim + 200f)
    )
}

// El loader mientras cargamos las actividades al listado
@Composable
fun CircularProgressIndicatorLoader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}


