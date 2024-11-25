package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun PlantasDeProtecaoScreen(
    modifier: Modifier = Modifier,
    navigateBackToCategories: () -> Unit // Função para voltar para a tela de categorias
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título da Tela
        Text(
            text = "Plantas de Proteção",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão de Voltar
        Button(
            onClick = { navigateBackToCategories() }, // Volta para a tela de Categorias
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Voltar para Categorias")
        }

        // Lista de Plantas de Proteção
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) { index -> // Vamos colocar 10 itens por enquanto
                PlantCard(index = index)
            }
        }
    }
}

@Composable
fun PlantCard(index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem da planta
        Image(
            painter = painterResource(id = R.drawable.planta_protecao_image_png), // Ajuste a imagem conforme necessário
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Texto da planta
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Planta ${index + 1}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Descrição breve da planta ${index + 1}.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantasDeProtecaoScreenPreview() {
    PlantCodeV3Theme {
        PlantasDeProtecaoScreen(
            navigateBackToCategories = {}
        )
    }
}
