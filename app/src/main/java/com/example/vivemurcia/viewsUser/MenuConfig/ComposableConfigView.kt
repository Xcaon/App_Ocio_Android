package com.example.vivemurcia.views.config

import android.R.attr.horizontalDivider
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.ui.theme.negroIconos
import com.example.vivemurcia.views.login.LoginView
import com.example.vivemurcia.viewsCompany.createActivity.Espaciado
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Inicio() {
    val context = LocalContext.current

    var valorTemaOscuro by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.plusjakartasansbold)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center, text = "Configuración del Perfíl"
            )
        }

        Espaciado(24)

        TextButton(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data =
                    Uri.parse("mailto:fernandohanloncuenca@gmail.com") // Dirección del destinatario
                putExtra(Intent.EXTRA_SUBJECT, "Sugerencia para mejorar la app ViveMurcia")
                putExtra(
                    Intent.EXTRA_TEXT,
                    """
Hola equipo de ViveMurcia,

Quería compartir una sugerencia para mejorar la app:

🔸 Idea o mejora que propongo:
[Escribe aquí tu sugerencia]

🔸 ¿Por qué crees que sería útil?
[Explica brevemente]

🔸 ¿Has detectado algún error relacionado?
[Indica si aplica]

Gracias por crear esta app, ¡sigo disfrutando de las actividades que descubrí gracias a ella!

Un saludo.
                """.trimIndent()
                )
            }

            // Abre la app de correo si hay una instalada
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No hay app de correo instalada, error: $e", Toast.LENGTH_SHORT).show()
            }

        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1.5f),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                    fontSize = 18.sp,
                    text = "Sugerencias"
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ir",
                    tint = negroIconos
                )
            }
        }

        TextButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://linktr.ee/fernandohanlon"))
            context.startActivity(intent)
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1.5f),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                    fontSize = 18.sp,
                    text = "Promociona tu negocio"
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ir",
                    tint = negroIconos
                )
            }
        }

        TextButton(onClick = {

        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1.5f),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                    fontSize = 18.sp,
                    text = "Claro/Oscuro"
                )
                Checkbox(
                    checked = valorTemaOscuro,
                    onCheckedChange = { it ->
                        valorTemaOscuro = it
                    }
                )
            }
        }

        TextButton(onClick = {
            Cerrar(context)
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1.5f),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.plusjakartasansregular)),
                    fontSize = 18.sp,
                    text = "Cerrar Sesión"
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ir",
                    tint = negroIconos
                )
            }
        }

    }
}


fun Cerrar(context: Context) {

    FirebaseAuth.getInstance().signOut()
    val intent = Intent(context, LoginView::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(context, intent, null)
}
