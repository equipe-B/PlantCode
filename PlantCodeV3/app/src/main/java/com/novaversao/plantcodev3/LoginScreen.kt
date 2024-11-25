package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit
) {
    // Gerenciando os estados dos campos de texto
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Campo de Login
        TextField(
            value = login.value,
            onValueChange = { login.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Usuário") },
            singleLine = true
        )

        // Campo de Senha
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // Botão "Acessar"
        Button(
            onClick = { navigateToHome() }, // Navega para a tela Home
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Acessar")
        }

        // Botão "Cadastrar"
        Button(
            onClick = { navigateToRegister() }, // Navega para a tela de Cadastro
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Cadastrar")
        }

        // Texto "Esqueceu sua senha?"
        TextButton(
            onClick = { navigateToForgotPassword() }, // Navega para a tela de Recuperação de Senha
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Esqueceu sua senha?")
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
            navigateToHome = {}
        )
    }
}
