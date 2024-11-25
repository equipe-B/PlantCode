package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    navigateToCategories: () -> Unit,
    navigateToSettings: () -> Unit // Parâmetro para navegar para a tela de Configurações
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botão para ir para a tela de Login
        Button(
            onClick = navigateToLogin,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Login")
        }

        // Botão para ir para a tela de Categorias
        Button(
            onClick = navigateToCategories,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Categorias")
        }

        // Botão para ir para a tela de Configurações
        Button(
            onClick = navigateToSettings,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Configurações")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlantCodeV3Theme {
        HomeScreen(
            navigateToLogin = {},
            navigateToCategories = {},
            navigateToSettings = {}
        )
    }
}
