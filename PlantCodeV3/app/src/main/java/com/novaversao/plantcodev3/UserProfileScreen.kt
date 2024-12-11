package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit // Função para voltar à tela anterior
) {
    // Estados para os campos do perfil
    var userName by remember { mutableStateOf("Nome do Usuário") }
    var userEmail by remember { mutableStateOf("email@example.com") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagem do perfil do usuário
        Image(
            painter = painterResource(id = R.drawable.placeholder_user_png), // Substitua pelo ID da imagem real
            contentDescription = "Foto do Usuário",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .padding(bottom = 16.dp)
        )

        // Campo para o Nome do Usuário
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors( // Usando a API estável
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF4CAF50),
                unfocusedIndicatorColor = Color.Gray
            ),
            textStyle = TextStyle(color = Color.Black) // Definindo a cor do texto
        )

        // Campo para o E-mail do Usuário
        TextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            label = { Text("E-mail") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors( // Usando a API estável
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF4CAF50),
                unfocusedIndicatorColor = Color.Gray
            ),
            textStyle = TextStyle(color = Color.Black) // Definindo a cor do texto
        )

        // Botão para Salvar Alterações
        Button(
            onClick = {
                println("Alterações salvas: Nome=$userName, E-mail=$userEmail")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Salvar Alterações", color = Color.White)
        }

        // Botão para Voltar
        Button(
            onClick = navigateBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Voltar", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    PlantCodeV3Theme {
        UserProfileScreen(
            navigateBack = {}
        )
    }
}
