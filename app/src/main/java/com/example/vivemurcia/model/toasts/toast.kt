package com.example.vivemurcia.model.toasts

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun AutoToastExample() {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    Column {
        Text("Este es un ejemplo de Toast automático")
        Button(onClick = { showToast = !showToast }) {
            Text("Cambiar clave")
        }
        LaunchedEffect(key1 = showToast) {
            Toast.makeText(context, "¡Toast automático!", Toast.LENGTH_SHORT).show()
        }
    }
}