@file:Suppress("DEPRECATION") // Para quitar los avisos de deprecado de todo el fichero

package com.example.vivemurcia.views.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig
import com.example.vivemurcia.ui.theme.VivemurciaTheme
import com.example.vivemurcia.views.home.HomeView
import com.example.vivemurcia.viewsUser.actividadInfo.InfoView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginView : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private companion object {
        private const val TAG = "GoogleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configuramos las preferencias de la app, mirar si hay algún fichero donde lo ejecute
        PreferencesConfig.preferenceSwitchValue(false, this)

        // Configure Google Sign In, Esto lanza la actividad de inicio de sesion de google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
                applicationContext.resources.getString(
                    applicationContext.resources.getIdentifier(
                        "default_web_client_id", "string", applicationContext.packageName
                    )
                )
            )
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        val usuarioActual = FirebaseAuth.getInstance().currentUser
        if (usuarioActual != null) {
            siguientePantalla(this, usuarioActual.displayName)
            overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)
        } else {
            setContent {
                VivemurciaTheme {
                    InitLogin {
                        signIn()
                    }
                }
            }
        }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        // Obtenemos el resultado de la actividad de Google, al seleccionar el usuario
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                // Se ha iniciado sesión correctamente, procesa el resultado.
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    Log.i("fernando", "El resultado es Ok")
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            } else {
                Log.i("fernando", "El resultado es CANCELED")
            }

        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // Nos devuelve la credencial para conseguir la credencial
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // 3 auth
        // Esta llamada guarda el usuario en Firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    // Obtener el usuario actual, aqui podemos obtener los diferentes datos
                    val user = auth.currentUser?.displayName

                    siguientePantalla(this, user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

}

fun siguientePantalla(context : Context, user: String?) {

    if (PreferencesConfig.comprobarInfoVisto(false, context)) {
        val intent = Intent(context, InfoView::class.java)
        intent.putExtra("user", user)
        (context as? Activity)?.startActivity(intent)
    } else {
        PreferencesConfig.preferenceSwitchValue(true, context)
        val intent = Intent(context, HomeView::class.java)
        intent.putExtra("user", user)
        (context as? Activity)?.startActivity(intent)
    }
}





