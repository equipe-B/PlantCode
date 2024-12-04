package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.novaversao.plantcodev3.model.Plantas
import com.novaversao.plantcodev3.model.PlantaRepository
import androidx.compose.foundation.border
import androidx.compose.ui.text.style.TextAlign
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import com.novaversao.plantcodev3.model.convertImageToBase64
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import android.content.Context
import android.provider.MediaStore
import java.io.IOException

@Composable
fun AddPlantScreen(
    modifier: Modifier = Modifier,
    plantaRepository: PlantaRepository, // Passa o repositório como parâmetro
    navigateBack: () -> Unit // Função de navegação para voltar para a tela anterior
) {
    var uploadSuccess by remember { mutableStateOf(false) }
    var base64Image by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) } // Adiciona variável para Bitmap
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
// Adiciona variável para Bitmap
    // Launcher para selecionar a imagem
    val launcher: ActivityResultLauncher<String> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            isLoading = true // Iniciar o carregamento
            base64Image = convertImageToBase64(context, uri)
            isLoading = false // Finalizar o carregamento
        }
    }
    // Estado dos campos de entrada
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var familia by remember { mutableStateOf("") }
    var modoDeUso by remember { mutableStateOf("") }
    var finalidades by remember { mutableStateOf("") }
    var partesUsadas by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Adicionar Nova Planta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Seção de upload da imagem
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clique aqui para fazer upload da imagem",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Selecionar Imagem")
            }

            // Exibir a imagem convertida ou mensagem de erro
            if (base64Image.isNotEmpty()) {
                Text(text = "Imagem convertida com sucesso!", color = androidx.compose.ui.graphics.Color.Green)
            } else if (imageUri != null) {
                Text(text = "Erro ao converter a imagem.", color = androidx.compose.ui.graphics.Color.Red)
            }

            // Indicador de carregamento
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }



        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        // Campos de entrada para os dados da planta
        TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoria") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = familia, onValueChange = { familia = it }, label = { Text("Família") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = modoDeUso, onValueChange = { modoDeUso = it }, label = { Text("Modo de Uso") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = finalidades, onValueChange = { finalidades = it }, label = { Text("Finalidades") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = partesUsadas, onValueChange = { partesUsadas = it }, label = { Text("Partes Usadas") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))


        // Botão para adicionar a planta
        Button(
            onClick = {
                // Cria um novo objeto Planta com a imagem em Base64
                val novaPlanta = Plantas(
                    descricao = descricao,
                    nome = nome,
                    categoria = categoria,
                    familia = familia,
                    modo_de_uso = modoDeUso,
                    finalidades = finalidades,
                    partes_usadas = partesUsadas,
                    imagemBase64 = base64Image // A imagem em Base64
                )

                // Chama o método AdicionarPlanta passando a nova planta e o bitmap
                plantaRepository.AdicionarPlanta(novaPlanta,base64Image) { plantaId ->
                    uploadSuccess = plantaId != null
                    if (uploadSuccess) {
                        // Sucesso na adição da planta
                        Log.d("AdicionarPlanta", "Planta adicionada com ID: $plantaId")
                        // Aqui você pode chamar navigateBack() se quiser voltar após o sucesso
                        navigateBack() // Descomente se quiser voltar após a adição
                    } else {
                        // Tratar erro ao adicionar planta
                        Log.e("AdicionarPlanta", "Erro ao adicionar planta")
                    }
                }
            },
            enabled = imageUri != null && base64Image.isNotEmpty(), // Habilita o botão apenas se uma imagem foi selecionada e convertida
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Planta")
        }
    }
}

