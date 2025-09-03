package com.example.vivemurcia.views.home


import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.vivemurcia.R
import com.example.vivemurcia.ui.theme.ThemeViewModel
import com.example.vivemurcia.ui.theme.colorCategoria
import com.example.vivemurcia.viewsCompany.ui.theme.VivemurciaTheme
import com.example.vivemurcia.viewsUser.MenuHome.pantallaDetalle.ViewModelDetalle


@AndroidEntryPoint
class HomeView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViveMurciaApp()
        }
    }
}


@Composable
fun ViveMurciaApp() {
    val themeViewModel: ThemeViewModel = hiltViewModel<ThemeViewModel>()

    LaunchedEffect(Unit) {
        themeViewModel.getTheme()
    }

    val isDark = themeViewModel.isDarkTheme.collectAsState()

    Log.d("darkTheme", "ViveMurciaApp: ${isDark.value}")
    VivemurciaTheme(isDarkTheme = isDark.value) {
        MyApp()
    }
}

// Como se pintara cada actividad, es decir, cada card
@Composable
fun ActividadCardCuadrada(actividad: Actividad, onClickActividad: (Actividad) -> Unit) {

    val cargando = remember { mutableStateOf(true) }
    // Fecha y hora
    val timestamp: Timestamp = actividad.fechaHoraActividad!! // de Firebase
    val calendar = Calendar.getInstance().apply {
        time = timestamp.toDate()
    }
    val formato = SimpleDateFormat("d MMMM", Locale("es", "ES"))
    val fechaBonita = formato.format(calendar.time)

    Column(
        modifier = Modifier
            .width(256.dp)
            .height(256.dp)
            .clickable {
                onClickActividad(actividad)
            }
    ) {
        if (cargando.value){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background( shimmerBrush())
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    model = actividad.uriImagen,
                    contentDescription = "Actividad",
                    contentScale = ContentScale.Crop,
                    onState = { it: AsyncImagePainter.State ->
                        if (it is AsyncImagePainter.State.Success) {
                            cargando.value = false
                        }
                    }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(if (cargando.value) shimmerBrush() else SolidColor(Color.Transparent))
                    .shadow(6.dp, RoundedCornerShape(12.dp), clip = false, Color.Black)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    model = actividad.uriImagen,
                    contentDescription = "Actividad",
                    contentScale = ContentScale.Crop,
                    onState = { it: AsyncImagePainter.State ->
                        if (it is AsyncImagePainter.State.Success) {
                            cargando.value = false
                        }
                    }
                )
            }
        }

        // Datos: Titulo y fecha de la actividad
        Column(
            modifier = Modifier
                .padding(
                    8.dp, 8.dp, 8.dp, 2.dp
                )
                .height(50.dp)
        ) {
            Text(
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                text = actividad.tituloActividad!!.replaceFirstChar { it.uppercase() },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                color = colorCategoria,
                fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                fontSize = 10.sp,
                text = actividad.categoriaActividad.toString()
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

// Como se pintara cada actividad, es decir, cada card
@Composable
fun ActividadCard(actividad: Actividad, onClickActividad: (Actividad) -> Unit) {
    val cargando = remember { mutableStateOf(true) }
    // Fecha y hora
    val timestamp: Timestamp = actividad.fechaHoraActividad!! // de Firebase
//    val calendar = Calendar.getInstance().apply {
//        time = timestamp.toDate()
//    }
//    val formato = SimpleDateFormat("d MMMM", Locale("es", "ES"))
//    val fechaBonita = formato.format(calendar.time)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val tarjetaAncho = (screenWidth - 32.dp) / 2

    Column(
        modifier = Modifier
            .width(tarjetaAncho)
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onClickActividad(actividad)
            }
    ) {

        if ( cargando.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .background(brush = shimmerBrush())
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(125.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    model = actividad.uriImagen,
                    contentDescription = "Imagen actividad",
                    contentScale = ContentScale.Crop,
                    onState = { it: AsyncImagePainter.State ->
                        if (it is AsyncImagePainter.State.Success) {
                            cargando.value = false
                        }
                    }
                )
            }
        } else {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .background(if (cargando.value) shimmerBrush() else SolidColor(Color.Transparent))
                    .shadow(6.dp, RoundedCornerShape(12.dp), clip = false, Color.Black)

            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(125.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    model = actividad.uriImagen,
                    contentDescription = "Imagen actividad",
                    contentScale = ContentScale.Crop,
                    onState = { it: AsyncImagePainter.State ->
                        if (it is AsyncImagePainter.State.Success) {
                            cargando.value = false
                        }
                    }
                )
            }
        }

        // Datos: Titulo y fecha de la actividad
        Column(
            modifier = Modifier
                .padding(
                    8.dp, 8.dp, 8.dp, 2.dp
                )
                .height(50.dp)
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
                fontSize = 12.sp,
                lineHeight = 12.sp,
                text = actividad.tituloActividad!!.replaceFirstChar { it.uppercase() },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                color = colorCategoria,
                fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                fontSize = 10.sp,
                text = actividad.categoriaActividad.toString()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

// Un Brush es un objeto que define cómo rellenar gráficamente una superficie
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


