package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun PlantDetailsScreen(
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
        // Imagem ilustrativa da planta
        Image(
            painter = painterResource(id = R.drawable.planta_cura_image_png), // Use uma imagem de exemplo
            contentDescription = "Planta ilustrativa",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título e descrição da planta
        Text(text = "Nome da Planta", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Categoria: Planta de Cura", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Descrição detalhada da planta será inserida aqui. Aqui estará a explicação completa sobre o uso e a importância dessa planta.",
            style = MaterialTheme.typography.bodyMedium
        )

        // Botão Voltar
        Spacer(modifier = Modifier.height(32.dp))
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
fun PlantDetailsScreenPreview() {
    PlantCodeV3Theme {
        PlantDetailsScreen(
            navigateBack = {}
        )
    }
}
