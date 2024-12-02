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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit,
    navigateBackToAccessType: () -> Unit // Função para voltar à tela de tipo de acesso
) {
    // Estados para capturar os dados de login e senha
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                label = { Text("Usuário") },
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
                onClick = navigateToHome,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Acessar", color = Color.White)
            }

            // Texto clicável para "Cadastrar"
            Text(
                text = "Cadastrar",
                color = Color(0xFF4CAF50),
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

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PlantCodeV3Theme {
        LoginScreen(
            navigateToRegister = {},
            navigateToForgotPassword = {},
            navigateToHome = {},
            navigateBackToAccessType = {}
        )
    }
}