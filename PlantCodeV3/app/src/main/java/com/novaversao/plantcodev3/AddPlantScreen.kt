package com.novaversao.plantcodev3
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.novaversao.plantcodev3.model.PlantaRepository
import com.novaversao.plantcodev3.model.convertImageToBase64
import androidx.compose.foundation.clickable
import com.novaversao.plantcodev3.model.Plantas
import android.util.Base64
import android.util.Log
import android.widget.Toast
import android.content.Context
import com.novaversao.plantcodev3.model.handleImageConversionAndSave
@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun AddPlantScreen(

    plantaRepository: PlantaRepository,
    navigateBack: () -> Unit
) {
    // State variables
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var familia by remember { mutableStateOf("") }
    var modoDeUso by remember { mutableStateOf("") }
    var finalidades by remember { mutableStateOf("") }
    var partesUsadas by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var base64Image by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isImageConverted by remember { mutableStateOf(false) }
    // Image selection launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            isLoading = true
            base64Image = convertImageToBase64(context, uri)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Nova Planta") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

// Image Upload Section
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .border(1.dp, Color.Gray)
                            .clickable { launcher.launch("image/*") }
                    ) {
                        if (imageUri != null) {
                            Image(
                                painter = rememberImagePainter(imageUri),
                                contentDescription = "Imagem da Planta",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                "Clique para selecionar imagem",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }


            Spacer(modifier = Modifier.height(16.dp))

            // Text Input Fields
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Planta") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = familia,
                onValueChange = { familia = it },
                label = { Text("Família") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = modoDeUso,
                onValueChange = { modoDeUso = it },
                label = { Text("Modo de Uso") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = finalidades,
                onValueChange = { finalidades = it },
                label = { Text("Finalidades") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = partesUsadas,
                onValueChange = { partesUsadas = it },
                label = { Text("Partes Usadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    // Verifica se há uma URI de imagem selecionada
                    imageUri?.let { uri ->
                        // Inicia o processo de conversão
                        isLoading = true
                        try {
                            // Conversão da imagem para Base64
                            base64Image = convertImageToBase64(context, uri)
                            // Define o estado de sucesso após a conversão
                            isImageConverted = true

                            // Chama a função para lidar com a validação e salvamento da planta
                            handleImageConversionAndSave(
                                base64Image,
                                context,
                                plantaRepository,
                                Plantas(
                                    imagemBase64 = base64Image,
                                    descricao = descricao,
                                    nome = nome,
                                    categoria = categoria,
                                    familia = familia,
                                    modo_de_uso = modoDeUso,
                                    finalidades = finalidades,
                                    partes_usadas = partesUsadas,
                                ),
                                navigateBack
                            )

                            // Opcional: Exibe um Toast de sucesso
                            Toast.makeText(
                                context,
                                "Imagem convertida com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            // Trata erros de conversão
                            isImageConverted = false

                            // Opcional: Exibe um Toast de erro
                            Toast.makeText(
                                context,
                                "Erro ao converter imagem: ${e.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()

                            // Log do erro para depuração
                            Log.e("ImageConversion", "Erro na conversão", e)
                        } finally {
                            // Finaliza o carregamento
                            isLoading = false
                        }
                    } ?: run {
                        // Caso nenhuma imagem tenha sido selecionada
                        Toast.makeText(
                            context,
                            "Selecione uma imagem primeiro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = imageUri != null && !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text("Salvar Planta")
                }
            }

            // Função para lidar com a conversão da imagem e o salvamento da planta
             fun handleImageConversionAndSave(
                base64Image: String,
                context: Context,
                plantaRepository: PlantaRepository,
                novaPlanta: Plantas,
                navigateBack: () -> Unit
            ) {
                // Verifica o tamanho da imagem em Base64
                val imageSizeInBytes = Base64.decode(base64Image, Base64.DEFAULT).size

                // Verifica se o tamanho da imagem excede 1 MB
                if (imageSizeInBytes > 1048576) { // 1 MB = 1048576 bytes
                    // Log e mensagem se a imagem exceder 1 MB
                    Log.w("AdicionarPlanta", "A imagem excede 1 MB. Tamanho: ${imageSizeInBytes} bytes")
                    Toast.makeText(context, "A imagem deve ser menor que 1 MB. Selecione outra imagem.", Toast.LENGTH_LONG).show()
                    return // Retorna sem adicionar a planta
                }

                // Chama a função AdicionarPlanta com um callback
                plantaRepository.AdicionarPlanta(novaPlanta, base64Image) { plantaId ->
                    if (plantaId != null) {
                        // A planta foi adicionada com sucesso
                        Toast.makeText(context, "Planta adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                        navigateBack() // Navega de volta após o sucesso
                    } else {
                        // Tratar erro ao adicionar a planta
                        Toast.makeText(context, "Erro ao adicionar a planta.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}