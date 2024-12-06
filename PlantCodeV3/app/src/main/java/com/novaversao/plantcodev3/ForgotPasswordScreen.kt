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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navigateBackToLogin: () -> Unit // Função para voltar à tela de Login
) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Plano de fundo
        Image(
            painter = painterResource(id = R.drawable.app_background), // Plano de fundo consistente
            contentDescription = "Plano de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50), // Cor verde
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de E-mail
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Confirmar", color = Color.White)
            }

            // Botão Voltar
            Button(
                onClick = { navigateBackToLogin() }, // Volta para a tela de Login
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Voltar", color = Color.White)
            }
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
