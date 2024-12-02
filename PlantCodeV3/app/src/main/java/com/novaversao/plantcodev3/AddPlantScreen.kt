package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddPlantScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit // Função de navegação para voltar para a tela anterior
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Exemplo de conteúdo da tela de adicionar planta
        Text("Tela para Adicionar Planta")

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para voltar
        Button(
            onClick = navigateBack, // Chama a função navigateBack quando o botão for clicado
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPlantScreenPreview() {
    AddPlantScreen(
        navigateBack = {}
    )
}
