package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit,
    navigateBackToAccessType: () -> Unit // Função para navegar de volta para a tela de escolha de tipo de acesso
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Campos de Login (usuário, senha, etc.)
        // ...

        // Botão "Acessar"
        Button(
            onClick = navigateToHome,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Acessar")
        }

        // Botão "Cadastrar"
        Button(
            onClick = navigateToRegister,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Cadastrar")
        }

        // Botão "Esqueceu a Senha?"
        Button(
            onClick = navigateToForgotPassword,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Esqueceu a Senha?")
        }

        // Botão "Voltar" para a Tela de Escolha de Tipo de Acesso
        Button(
            onClick = navigateBackToAccessType,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar à Escolha do Tipo de Acesso")
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
