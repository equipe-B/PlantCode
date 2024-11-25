package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun PlantasDeCuraScreen(
    modifier: Modifier = Modifier,
    navigateBackToCategories: () -> Unit
) {
    val plantList: List<Int> = List(10) { it }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Plantas de Cura",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { navigateBackToCategories() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Voltar para Categorias")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(plantList) { index ->
                CuraPlantCard(index = index) // Função renomeada para evitar ambiguidade
            }
        }
    }
}

@Composable
fun CuraPlantCard(index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.planta_cura_image_png),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

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
fun PlantasDeCuraScreenPreview() {
    PlantCodeV3Theme {
        PlantasDeCuraScreen(
            navigateBackToCategories = {}
        )
    }
}
