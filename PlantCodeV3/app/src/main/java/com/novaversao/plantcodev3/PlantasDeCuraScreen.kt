package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun PlantasDeCuraScreen(
    navigateBackToCategories: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToQRCodeScanner: () -> Unit,
    navigateToAddPlant: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val plantList: List<Int> = List(10) { it }

    Box(modifier = Modifier.fillMaxSize()) {
        // Plano de fundo
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = "Plano de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Plantas de Cura",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { navigateBackToCategories() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = "Voltar para Categorias", color = Color.White)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(plantList) { index ->
                    CuraPlantCard(index = index)
                }
            }
        }

        // Barra de Navegação Inferior com ícones padrão
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = navigateToHome) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Início",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToQRCodeScanner) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Câmera",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToAddPlant) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adicionar",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToSettings) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Configurações",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
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
            Text(text = "Planta ${index + 1}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "Descrição breve da planta ${index + 1}.", fontSize = 14.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantasDeCuraScreenPreview() {
    PlantCodeV3Theme {
        PlantasDeCuraScreen(
            navigateBackToCategories = {},
            navigateToHome = {},
            navigateToQRCodeScanner = {},
            navigateToAddPlant = {},
            navigateToSettings = {}
        )
    }
}
