package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@Composable
fun AccessTypeScreen(
    modifier: Modifier = Modifier,
    navigateToAdminLogin: () -> Unit, // Navega para o login do administrador
    navigateToVisitorLogin: () -> Unit // Navega para o login do visitante
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logotipo
        Image(
            painter = painterResource(id = R.drawable.logo_ifam),
            contentDescription = "Logotipo do PlantCode",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        // Título do app
        Text(
            text = "PlantCode",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Mensagem de boas-vindas
        Text(
            text = "Seja bem-vindo",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Mensagem para selecionar o vínculo
        Text(
            text = "Selecione seu vínculo",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão para Administrador
        Button(
            onClick = navigateToAdminLogin,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde claro
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Administrador", color = Color.White)
        }

        // Botão para Visitante
        Button(
            onClick = navigateToVisitorLogin,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde claro
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Visitante", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccessTypeScreenPreview() {
    PlantCodeV3Theme {
        AccessTypeScreen(
            navigateToAdminLogin = {},
            navigateToVisitorLogin = {}
        )
    }
}
