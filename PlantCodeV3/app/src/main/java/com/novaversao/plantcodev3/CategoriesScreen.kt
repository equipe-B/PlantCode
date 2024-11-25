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
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navigateToPlantasDeCura: () -> Unit,
    navigateToPlantasDeProtecao: () -> Unit,
    navigateToAddPlant: () -> Unit,
    navigateToQRCodeScanner: () -> Unit,
    navigateToSettings: () -> Unit // Parâmetro para navegação para a tela de Configurações
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botão para ir para a tela de Plantas de Cura
        Button(
            onClick = navigateToPlantasDeCura,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Plantas de Cura")
        }

        // Botão para ir para a tela de Plantas de Proteção
        Button(
            onClick = navigateToPlantasDeProtecao,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Plantas de Proteção")
        }

        // Botão para ir para a tela de Adicionar Planta
        Button(
            onClick = navigateToAddPlant, // Navegação para Adicionar Planta
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Adicionar Planta")
        }

        // Botão para ir para a tela de Leitura de QR Code
        Button(
            onClick = navigateToQRCodeScanner, // Navegação para a tela de Leitura de QR Code
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Ler QR Code")
        }

        // Botão para ir para a tela de Configurações
        Button(
            onClick = navigateToSettings, // Navegação para a tela de Configurações
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Configurações")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    PlantCodeV3Theme {
        CategoriesScreen(
            navigateToPlantasDeCura = {},
            navigateToPlantasDeProtecao = {},
            navigateToAddPlant = {},
            navigateToQRCodeScanner = {},
            navigateToSettings = {}
        )
    }
}
