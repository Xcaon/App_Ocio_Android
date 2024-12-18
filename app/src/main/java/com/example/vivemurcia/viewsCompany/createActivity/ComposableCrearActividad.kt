package com.example.vivemurcia.viewsCompany.createActivity

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivemurcia.R
import com.example.vivemurcia.model.clases.Actividad
import com.example.vivemurcia.model.enums.EnumAmbiente
import com.example.vivemurcia.model.enums.EnumCategories
import com.example.vivemurcia.model.enums.EnumGrupos
import com.example.vivemurcia.ui.theme.fondoPantalla
import com.example.vivemurcia.viewsCompany.ui.theme.botonNaranja
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Preview(showBackground = true)
@Composable
fun InicioCrearActividad() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPantalla)
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Gestionar los valores del formulario
        var tituloServicio: MutableState<String> = remember { mutableStateOf("") }
        var descripcionServicio: MutableState<String> = remember { mutableStateOf("") }
//        var fechaServicio = remember { mutableStateOf("") } // TODO()
        var categoriaActividad: MutableState<EnumCategories> =
            remember { mutableStateOf(EnumCategories.AVENTURAS) }
        var localizacionActividad: MutableState<String> = remember { mutableStateOf("") }
        var ubicacionServicio: MutableState<String> = remember { mutableStateOf("") }
        var tipoDeGrupo: MutableState<EnumGrupos> = remember { mutableStateOf(EnumGrupos.FAMILIAR) }
        var ambienteActividad: MutableState<EnumAmbiente> =
            remember { mutableStateOf(EnumAmbiente.EXTERIOR) }

        // Gestion de errores
        var nombreError by remember { mutableStateOf(true) }


        // Vistas Compose
        Imagen() // TODO()
        NombreTitulo(tituloServicio, nombreError)
        DescripcionServicio(descripcionServicio)
        FechaServicio() // Hay que ver como hacerlo con la fecha
        CategoriaActividad {
            categoriaActividad.value = it
        }
        LocalizacionServicioGoogleMaps(localizacionActividad)
        UbicacionServicio(ubicacionServicio)
        TipoDeGrupo { tipoDeGrupo.value = it }
        AmbienteServicio {
            ambienteActividad.value = it
            Log.i("fernando", "Se ha guardado el ambiente $it")
        }
        ReservasServicio()

        // Guardar opciones del formulario
        BotonesCrearActividad {
            var crearActividad = Actividad(
                idActividad = null,
                imagenActividad = null, // de momento cogemos el por defecto
                tituloActividad = tituloServicio.value,
                fechaHoraActividad = Timestamp.now(),
                ambienteActividad = ambienteActividad.value,
                categoriaActividad = categoriaActividad.value,
                descripcionActividad = descripcionServicio.value,
                localizacionActividad = localizacionActividad.value,
                tipoDeGrupo = tipoDeGrupo.value,
                ubicacionActividad = ubicacionServicio.value
            )
            Log.i("fernando", "Se ha guardado la actividad $crearActividad")
//          creaActividadModel()
        }
    }
}

@Composable
fun Espaciado(espaciado: Int = 16) {
    Spacer(modifier = Modifier.height(espaciado.dp))
}

@Composable
fun TipoDeGrupo(onSave: (EnumGrupos) -> Unit) {
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está abierto

    // Opción seleccionada
    var selectedOption by remember { mutableStateOf("Seleccione una opción") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Grupo seleccionado: $selectedOption")
        Espaciado(8)
        // Botón para abrir el menú desplegable
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = botonNaranja,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text("Seleccione el tipo de Grupo")
        }

        // Menú desplegable
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false } // Cierra el menú al hacer clic afuera
        ) {
            EnumGrupos.entries.forEach { option: EnumGrupos ->
                DropdownMenuItem(text = { Text(option.nombre) }, onClick = {
                    onSave(option)
                    selectedOption = option.nombre // Actualiza la opción seleccionada
                    expanded = false // Cierra el menú
                })
            }
        }
    }
    Espaciado()
}

@Composable
fun BotonesCrearActividad(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonColors(
            containerColor = botonNaranja,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text("Guardar Cambios", fontSize = 20.sp)
    }
    Espaciado()
}

@Suppress("DEPRECATION")
@Preview(showBackground = true)
@Composable
fun ReservasServicio() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        OutlinedCard(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Click para configurar el Sistema de Reservas", fontSize = 16.sp)
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Icono")
            }
        }
    }
    Espaciado()
}

@Composable
fun AmbienteServicio(opcionEscogida: (EnumAmbiente) -> Unit) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp, 8.dp, 8.dp, 4.dp)
                .padding(8.dp)
        ) {
            Text("Ambiente")
            Spacer(modifier = Modifier.height(4.dp))
            Row {

                val (selectedOption, onOptionSelected) = remember { mutableStateOf(EnumAmbiente.EXTERIOR) } // El estado ahora es EnumAmbiente

                EnumAmbiente.entries.forEach { ambiente -> // Iterar a través de los valores de EnumAmbiente
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (ambiente == selectedOption),
                            onClick = {
                                onOptionSelected(ambiente) // Actualizar el estado con EnumAmbiente
                                opcionEscogida(ambiente) // Pasar EnumAmbiente a la lambda
                            }
                        )
                        Text(ambiente.nombre) // Mostrar la propiedad nombre
                    }
                }

            }

        }
    }
    Espaciado()
}

@Composable
fun UbicacionServicio(ubicacionServicio: MutableState<String>) {
    Text("Dirección del lugar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = ubicacionServicio.value,
        onValueChange = { textoIntroducido -> ubicacionServicio.value = textoIntroducido },
        label = { Text(text = "Introduce la dirección del lugar manualmente") },
        modifier = Modifier.fillMaxWidth()
    )
    Espaciado(32)
}

@Composable
fun LocalizacionServicioGoogleMaps(localizacionActividad: MutableState<String>) {
    Text("Url de Google Maps", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = localizacionActividad.value,
        onValueChange = { value -> localizacionActividad.value = value },
        label = { Text(text = "Introduce la url de Google Maps") },
        modifier = Modifier.fillMaxWidth()
    )
    Espaciado()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaActividad(categoriaActividad: (EnumCategories) -> Unit) {

    var expanded by remember { mutableStateOf(false) } // Controla si el menú está abierto

    // Opción seleccionada
    var selectedOption by remember { mutableStateOf("Seleccione una opción") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Categoría seleccionada: $selectedOption", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        // Botón para abrir el menú desplegable
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = botonNaranja,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text("Seleccione la Categoría de la Actividad", fontSize = 16.sp)
        }

        // Menú desplegable
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false } // Cierra el menú al hacer clic afuera
        ) {
            EnumCategories.entries.forEach { option: EnumCategories ->
                DropdownMenuItem(text = { Text(option.nombre) }, onClick = {
                    selectedOption = option.nombre // Actualiza la opción seleccionada
                    categoriaActividad(option)
                    expanded = false // Cierra el menú
                })
            }
        }
    }
    Espaciado(32)
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
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Fecha seleccionada: ${selectedDate.format(formatter)}", fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = botonNaranja,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            ) {
                Text("Seleccionar fecha", fontSize = 16.sp)
            }
        }
    }

    // Diálogo de DatePicker
    if (showDatePicker) {
        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    selectedDate = LocalDate.ofEpochDay(millis / 86400000)
                }
                showDatePicker = false
            }) {
                Text("OK")
            }
        }, dismissButton = {
            TextButton(onClick = { showDatePicker = false }) {
                Text("Cancelar")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }
    Espaciado()
}

@Composable
fun DescripcionServicio(descripcionServicio: MutableState<String>) {
    Text("Descripción", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = "",
        onValueChange = { value -> descripcionServicio.value = value },
        label = { Text(text = "Introduce en que consiste la actividad que se va a realizar.") },
        modifier = Modifier.fillMaxWidth().height(100.dp)
    )
    Espaciado(32)
}

@Composable
fun NombreTitulo(tituloServicio: MutableState<String>, nombreError: Boolean) {
    Text("Nombre del titulo", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = "",
        onValueChange = { value -> tituloServicio.value = value },
        label = { Text(text = "Titulo de la actividad *") },
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
//        isError = nombreError
    )
    Espaciado()
}

@Composable
fun Imagen() {
    Espaciado()
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
    Espaciado()
}