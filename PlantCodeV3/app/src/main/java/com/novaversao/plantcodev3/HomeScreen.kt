package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.PhotoCamera // Import do ícone de câmera
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun HomeScreen(
    navigateToCategories: () -> Unit,
    navigateToQRCodeScanner: () -> Unit,
    navigateToAddPlant: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToPlantasDeCura: () -> Unit,
    navigateToPlantasDeProtecao: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

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
            // Caixa de Pesquisa com ícone de lupa
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(text = "Pesquisar Plantas") },
                leadingIcon = {
                    IconButton(
                        onClick = { /* A funcionalidade de pesquisa será implementada depois */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search, // Ícone padrão de pesquisa
                            contentDescription = "Ícone de Pesquisa",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagem ilustrativa de QR Code
            Image(
                painter = painterResource(id = R.drawable.ic_qr_code_illustration),
                contentDescription = "Imagem de identificação com QR Code",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Categorias
            Button(
                onClick = navigateToCategories,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Categorias", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Botões para Plantas de Cura e Plantas de Proteção
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = navigateToPlantasDeCura,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text(text = "Plantas de Cura", color = Color.White)
                }
                Button(
                    onClick = navigateToPlantasDeProtecao,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text(text = "Plantas de Proteção", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Últimas Visualizações
            Text(
                text = "Últimas Visualizações",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder_last_viewed),
                contentDescription = "Última planta visualizada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }

        // Barra de Navegação Inferior com ícones padrão
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Já está na Home */ }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Início",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = navigateToQRCodeScanner) {
                Icon(
                    imageVector = Icons.Rounded.PhotoCamera, // Ícone de câmera atualizado
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlantCodeV3Theme {
        HomeScreen(
            navigateToCategories = {},
            navigateToQRCodeScanner = {},
            navigateToAddPlant = {},
            navigateToSettings = {},
            navigateToPlantasDeCura = {},
            navigateToPlantasDeProtecao = {}
        )
    }
}
