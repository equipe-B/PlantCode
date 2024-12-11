package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable // Importação para o clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
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
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit,
    navigateBackToAccessType: () -> Unit // Função para voltar à tela de tipo de acesso
) {
    // Estados para capturar os dados de login e senha
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = Firebase.auth
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

    fun signInWithGoogle() {
        isLoading = true
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Plano de fundo com imagem
        Image(
            painter = painterResource(id = R.drawable.app_background), // Referência ao plano de fundo
            contentDescription = "Plano de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Conteúdo principal da tela
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Campo de entrada de usuário
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("E-mail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Campo de entrada de senha
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation() // Para esconder a senha
            )

            // Botão "Acessar"
            Button(
                onClick = {
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Por favor, insira seu usuário e senha.", Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    // Realiza a autenticação com Firebase
                    auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navigateToHome()
                            }
                            else {
                                Toast.makeText(context, "Login ou senha não cadastrados", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Acessar", color = Color.White)
            }


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
            // Texto clicável para "Cadastrar"
            Text(
                text = "Cadastrar",
                color = Color(0xFFA4C5C0),
                modifier = Modifier.clickable(onClick = navigateToRegister).padding(bottom = 8.dp)
            )

            // Texto clicável para "Esqueceu a senha?"
            Text(
                text = "Esqueceu a senha?",
                color = Color(0xFF4CAF50),
                modifier = Modifier.clickable(onClick = navigateToForgotPassword).padding(bottom = 8.dp)
            )

            // Texto clicável para "Voltar ao tipo de acesso"
            Text(
                text = "Voltar ao tipo de acesso",
                color = Color(0xFF4CAF50),
                modifier = Modifier.clickable(onClick = navigateBackToAccessType).padding(top = 16.dp)
            )
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



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PlantCodeV3Theme {
        LoginScreen(
            navigateToRegister = {},
            navigateToForgotPassword = {},
            navigateToHome = {},
            navigateBackToAccessType = {},
            onLoginSuccess = {}
        )
    }
}