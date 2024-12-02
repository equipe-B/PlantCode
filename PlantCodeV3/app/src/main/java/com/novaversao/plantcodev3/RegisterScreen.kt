package com.novaversao.plantcodev3

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateBackToLogin: () -> Unit, // Função para voltar à tela de Login
    navigateToHome: () -> Unit // Navega para a tela inicial após cadastro
) {
    // Estados para capturar os dados de cadastro
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun validateAndRegister() {
        when {
            email.isEmpty() -> Toast.makeText(context, "E-mail é obrigatório", Toast.LENGTH_SHORT).show()
            username.isEmpty() -> Toast.makeText(context, "Nome de usuário é obrigatório", Toast.LENGTH_SHORT).show()
            password.isEmpty() -> Toast.makeText(context, "Senha é obrigatória", Toast.LENGTH_SHORT).show()
            confirmPassword.isEmpty() -> Toast.makeText(context, "Confirme a senha", Toast.LENGTH_SHORT).show()
            password != confirmPassword -> Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            else -> {
                Toast.makeText(context, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
                navigateToHome() // Navega para a tela inicial após sucesso
            }
        }
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
            // Campo de E-mail
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text("E-mail") },
                singleLine = true
            )

            // Campo de Nome de Usuário
            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text("Nome de Usuário") },
                singleLine = true
            )

            // Campo de Senha
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text("Senha") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            // Campo de Confirmar Senha
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text("Confirmar Senha") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            // Botão Cadastrar
            Button(
                onClick = { validateAndRegister() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde claro
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = "Cadastrar", color = Color.White)
            }

            // Botão Cancelar
            Button(
                onClick = { navigateBackToLogin() }, // Volta para a tela de Login
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde claro
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = "Cancelar", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    PlantCodeV3Theme {
        RegisterScreen(
            navigateBackToLogin = {},
            navigateToHome = {}
        )
    }
}
