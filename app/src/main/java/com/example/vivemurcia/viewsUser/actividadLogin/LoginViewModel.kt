package com.example.vivemurcia.viewsUser.actividadLogin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivemurcia.model.sharedPreferences.PreferencesConfig.savePreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) : ViewModel() {

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "GoogleActivity"
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Configuración de Google Sign-In
    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(
            context.resources.getString(
                context.resources.getIdentifier(
                    "default_web_client_id", "string", context.packageName
                )
            )
        )
        .requestEmail()
        .build()

    fun initGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent() = googleSignInClient.signInIntent

    fun handleSignInResult(
        data: android.content.Intent?,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!, onSuccess, onError)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            onError(e)
        }
    }

    private fun firebaseAuthWithGoogle(
        idToken: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithCredential:success")
                    val userIdToken = auth.currentUser?.uid!!
                    viewModelScope.launch(Dispatchers.IO) {
                        savePreferences(context, userIdToken, true)
                        onSuccess(userIdToken)
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    onError(task.exception ?: Exception("Unknown Firebase error"))
                }
            }
    }
}
