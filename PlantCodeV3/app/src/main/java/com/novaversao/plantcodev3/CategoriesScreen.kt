package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Settings
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
fun CategoriesScreen(
    navigateToPlantasDeCura: () -> Unit,
    navigateToPlantasDeProtecao: () -> Unit,
    navigateToAddPlant: () -> Unit,
    navigateToQRCodeScanner: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit // Navegação para a tela inicial
) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botão para ir para a tela de Plantas de Cura
            Button(
                onClick = navigateToPlantasDeCura,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = "Plantas de Cura", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Botão para ir para a tela de Plantas de Proteção
            Button(
                onClick = navigateToPlantasDeProtecao,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = "Plantas de Proteção", color = Color.White, fontWeight = FontWeight.Bold)
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
                    imageVector = Icons.Filled.Home, // Ícone padrão de Home
                    contentDescription = "Início",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToQRCodeScanner) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera, // Ícone padrão de câmera
                    contentDescription = "Câmera",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToAddPlant) {
                Icon(
                    imageVector = Icons.Filled.Add, // Ícone padrão de Adicionar
                    contentDescription = "Adicionar",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToSettings) {
                Icon(
                    imageVector = Icons.Filled.Settings, // Ícone padrão de Configurações
                    contentDescription = "Configurações",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
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
            navigateToSettings = {},
            navigateToHome = {}
        )
    }
}
