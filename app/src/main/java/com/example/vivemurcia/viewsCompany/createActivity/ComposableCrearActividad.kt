package com.example.vivemurcia.viewsCompany.createActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivemurcia.R
import com.example.vivemurcia.ui.theme.fondoPantalla
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun InicioCrearActividad() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPantalla)
            .padding(16.dp)
    ) {
        Imagen()
        Spacer(modifier = Modifier.height(16.dp))
        NombreServicio()
        Spacer(modifier = Modifier.height(16.dp))
        DescripcionServicio()
        FechaServicio()
//        CategoriaServicio()
//        LocalizacionServicioGoogleMaps()
//        UbicacionServicio()
//        AmbienteServicio()
//        ReservasServicio()
    }
}

@Composable
fun ReservasServicio() {
    TODO("Not yet implemented")
}

@Composable
fun AmbienteServicio() {
    TODO("Not yet implemented")
}

@Composable
fun UbicacionServicio() {
    TODO("Not yet implemented")
}

@Composable
fun LocalizacionServicioGoogleMaps() {
    TODO("Not yet implemented")
}

@Composable
fun CategoriaServicio() {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaServicio() {
    // Estado para mostrar el diálogo de fecha
    var showDatePicker by remember { mutableStateOf(false) }

    // Estado para almacenar la fecha seleccionada
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Estado del DatePicker
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * 86400000
    )

    // Formateador de fechas
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Layout principal
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column( modifier = Modifier.fillMaxWidth() ) {
            Button(onClick = { showDatePicker = true }) {
                Text("Seleccionar fecha")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fecha seleccionada: ${selectedDate.format(formatter)}",
                fontSize = 18.sp
            )
        }
    }

    // Diálogo de DatePicker
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = LocalDate.ofEpochDay(millis / 86400000)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun DescripcionServicio() {

    var descripcion = remember { mutableStateOf("") }

    OutlinedTextField(
        value = descripcion.value,
        onValueChange = {},
        label = { Text(text = "Descripción de la actividad") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NombreServicio() {

    var nombreServicio = remember { mutableStateOf("") }

    OutlinedTextField(
        value = nombreServicio.value,
        onValueChange = {},
        label = { Text(text = "Nombre de la actividad") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Imagen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.explorador),
            contentDescription = "SuperHero Avatar",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}