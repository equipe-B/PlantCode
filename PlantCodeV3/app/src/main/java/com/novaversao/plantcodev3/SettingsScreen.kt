package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme
import androidx.compose.foundation.clickable


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit, // Navega para a tela de perfil do usuário
    navigateBack: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Adicionando plano de fundo
        Image(
            painter = painterResource(id = R.drawable.app_background),
            contentDescription = "Plano de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho com foto do usuário e "Conta"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto do usuário
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Foto do usuário",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Nome e botão "Conta"
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nome do Usuário",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Conta",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.clickable(onClick = navigateToUserProfile)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Configuração de Notificações
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Notificações", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4CAF50))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuração de Tema Escuro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Modo Escuro", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4CAF50))
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão de Voltar
            Button(
                onClick = navigateBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Voltar", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    PlantCodeV3Theme {
        SettingsScreen(
            navigateToUserProfile = {},
            navigateBack = {}
        )
    }
}