package com.novaversao.plantcodev3

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


@Composable
fun LoginScreenn(
    onLoginSuccess: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateBackToAccessType: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Contexto e Firebase Auth
    val context = LocalContext.current
    val auth = Firebase.auth

// Estados
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

// Configuração do Google Sign-In
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

// Launcher para resultado de login do Google
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    firebaseAuthWithGoogle(
                        account.idToken!!,
                        auth,
                        onLoginSuccess = {
                            isLoading = false
                            onLoginSuccess()
                        },
                        onError = { message ->
                            isLoading = false
                            errorMessage = message
                        }
                    )
                }
            } catch (e: ApiException) {
                isLoading = false
                errorMessage = "Erro de autenticação com Google: ${e.message}"
                Log.e("GoogleSignIn", "Erro de login", e)
            }
        }
    }
    //Função
    fun signInWithGoogle() {
        isLoading = true
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo ou Título
            Text(
                text = "PlantCode",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Senha
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de Login
            Button(
                onClick = {
                    onLoginSuccess()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divisor
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Text("ou", color = Color.Gray)
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Login com Google
            OutlinedButton(
                onClick = {signInWithGoogle() }, enabled = !isLoading) {
                if (isLoading) {
                    CircularProgressIndicator()
                }
                else {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continuar com Google")
                }
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Link para Cadastro
            TextButton(onClick = {
                // Navegação para tela de cadastro
            }) {
                Text(
                    text = "Não tem uma conta? Cadastre-se",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun saveUserToFirestore(user: FirebaseUser, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val userRef = firestore.collection("users").document(user.uid)

    val userData = hashMapOf(
        "uid" to user.uid,
        "email" to (user.email ?: ""),
        "displayName" to (user.displayName ?: ""),
        "photoUrl" to (user.photoUrl?.toString() ?: ""),
        "createdAt" to FieldValue.serverTimestamp()
    )

    userRef.set(userData, SetOptions.merge())
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onError(e.message ?: "Erro ao salvar usuário")
        }
}


fun firebaseAuthWithGoogle(
    idToken: String,
    auth: FirebaseAuth,
    onLoginSuccess: () -> Unit, onError: (String) -> Unit) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Login bem-sucedido
                val user = auth.currentUser
                user?.let {
                    saveUserToFirestore(
                        user,
                        onSuccess = {
                            onLoginSuccess()
                        },
                        onError = { errorMessage ->
                            onError(errorMessage)
                        }
                    )
                } ?: onError("Usuário não encontrado")
            } else {
                // Tratamento de erro
                onError("Falha na autenticação: ${task.exception?.message}")
            }
        }
}
