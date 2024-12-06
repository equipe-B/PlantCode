package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun AddPlantScreen(
    navigateToHome: () -> Unit,
    navigateToQRCodeScanner: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAddPlant: () -> Unit
) {
    var showForm by remember { mutableStateOf(false) }
    var scientificName by remember { mutableStateOf("") }
    var plantClass by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var family by remember { mutableStateOf("") }

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
            Text(
                text = "Adicionar Planta",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (showForm) {
                // Campos de texto para adicionar informações da planta
                OutlinedTextField(
                    value = scientificName,
                    onValueChange = { scientificName = it },
                    label = { Text("Nome Científico") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = plantClass,
                    onValueChange = { plantClass = it },
                    label = { Text("Classe") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Espécie") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = family,
                    onValueChange = { family = it },
                    label = { Text("Família") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Botão "Salvar"
                Button(
                    onClick = {
                        // Lógica de salvar (pode ser ajustada para incluir backend ou banco de dados)
                        println("Planta salva: $scientificName, $plantClass, $species, $family")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(text = "Salvar", color = Color.White)
                }
            } else {
                // Botão inicial para mostrar o formulário
                Button(
                    onClick = { showForm = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text("Adicionar Planta", color = Color.White)
                }
            }
        }

        // Barra de navegação inferior
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
                    imageVector = Icons.Filled.CameraAlt,
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
fun AddPlantScreenPreview() {
    PlantCodeV3Theme {
        AddPlantScreen(
            navigateToHome = {},
            navigateToQRCodeScanner = {},
            navigateToSettings = {},
            navigateToAddPlant = {}
        )
    }
}
