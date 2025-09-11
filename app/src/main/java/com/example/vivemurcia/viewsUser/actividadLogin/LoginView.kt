@file:Suppress("DEPRECATION") // Para quitar los avisos de deprecado de todo el fichero

package com.example.vivemurcia.views.login

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig.savePreferences
import com.example.vivemurcia.views.home.HomeView
import com.example.vivemurcia.viewsCompany.ui.theme.VivemurciaTheme
import com.example.vivemurcia.viewsUser.actividadInfo.InfoView
import com.example.vivemurcia.viewsUser.actividadLogin.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginView : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.handleSignInResult(
                    result.data,
                    onSuccess = { userId ->
                        val intent = Intent(this, InfoView::class.java)
                        startActivity(intent)
                    },
                    onError = { e ->
                        Log.e("LoginView", "Error sign in: $e")
                    })
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val usuarioActual = viewModel.getCurrentUser()
            if (usuarioActual != null) {
                    val intent = Intent(this@LoginView, InfoView::class.java)
                    this@LoginView.startActivity(intent)
            } else {
                // Para iniciar login
                InitLogin() { Signintent ->
                    resultLauncher.launch(Signintent)
                }
            }

        }

    }
}




