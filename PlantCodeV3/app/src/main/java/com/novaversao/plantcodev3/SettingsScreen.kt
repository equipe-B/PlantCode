package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit // Função para voltar para a tela anterior
) {
    // Estado para controlar as preferências de configuração
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Configurações",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Configuração de Notificações
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Notificações", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Configuração de Tema
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Modo Escuro", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de Salvar Configurações
        Button(
            onClick = {
                // Lógica para salvar as configurações pode ser adicionada aqui
                println("Configurações Salvas: Notificações=$notificationsEnabled, Modo Escuro=$darkModeEnabled")
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Salvar Configurações")
        }

        // Botão de Voltar
        Button(
            onClick = navigateBack, // Agora navegando corretamente para a tela anterior
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    PlantCodeV3Theme {
        SettingsScreen(
            navigateBack = {}
        )
    }
}
