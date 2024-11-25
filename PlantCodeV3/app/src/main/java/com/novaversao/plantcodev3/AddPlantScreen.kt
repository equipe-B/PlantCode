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
fun AddPlantScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit // Função para voltar à tela anterior
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título da tela
        Text(text = "Adicionar Planta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada para a planta
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Nome da Planta") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Categoria") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Botão de salvar ou cancelar
        Button(
            onClick = { /* Lógica para salvar a planta */ },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Salvar Planta")
        }

        // Botão Voltar
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navigateBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPlantScreenPreview() {
    PlantCodeV3Theme {
        AddPlantScreen(
            navigateBack = {}
        )
    }
}
