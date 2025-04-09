package com.example.vivemurcia.views.config

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.rememberNavController
import com.example.vivemurcia.views.login.LoginView
import com.google.firebase.auth.FirebaseAuth

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Inicio() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            Cerrar(context)
        }) {
            Text("Cerrar Sesión")
        }
    }

}


fun Cerrar(context: Context) {

    FirebaseAuth.getInstance().signOut()
    val intent = Intent(context, LoginView::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(context, intent, null)
}
