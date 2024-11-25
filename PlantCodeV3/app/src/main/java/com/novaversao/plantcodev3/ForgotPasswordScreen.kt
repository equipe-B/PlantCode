package com.novaversao.plantcodev3

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navigateBackToLogin: () -> Unit // Função para voltar à tela de Login
) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto Explicativo
        Text(
            text = "Insira seu e-mail para recuperar a senha",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo de E-mail
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            label = { Text("E-mail") },
            singleLine = true
        )

        // Botão Confirmar
        Button(
            onClick = {
                if (email.isEmpty()) {
                    Toast.makeText(context, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Instruções enviadas para $email", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Confirmar")
        }

        // Botão Voltar
        Button(
            onClick = { navigateBackToLogin() }, // Volta para a tela de Login
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    PlantCodeV3Theme {
        ForgotPasswordScreen(navigateBackToLogin = {})
    }
}
