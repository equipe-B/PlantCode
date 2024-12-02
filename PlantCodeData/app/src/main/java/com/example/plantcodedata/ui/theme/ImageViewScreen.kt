package com.example.plantcodedata.ui.theme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.material.MaterialTheme

// Data class to represent an image item
data class ImageItem(val id: String, val base64: String)

// Data class to represent a plant item
data class PlantItem(
    val id: String,
    val descricao: String,
    val nome: String,
    val categoria: String,
    val familia: String,
    val modoDeUso: String,
    val finalidades: String,
    val partesUsadas: String,
    val base64Image: String
)

// Função para buscar todas as imagens em Base64 do Firestore
suspend fun fetchImagesFromFirestore(): List<ImageItem> {
    val db = FirebaseFirestore.getInstance()
    return try {
        val documents = db.collection("images").get().await().documents
        documents.map { doc ->
            val base64 = doc.getString("image") ?: ""
            ImageItem(doc.id, base64) // Retorna uma lista de ImageItem
        }
    } catch (e: Exception) {
        Log.e("Firestore", "Erro ao buscar imagens: ${e.message}")
        emptyList() // Em caso de erro, retorna uma lista vazia
    }
}

// Função para buscar uma planta específica do Firestore
suspend fun fetchPlantById(plantId: String): PlantItem? {
    val db = FirebaseFirestore.getInstance()
    return try {
        val document = db.collection("plantas").document(plantId).get().await()
        if (document.exists()) {
            val descricao = document.getString("descricao") ?: ""
            val nome = document.getString("nome") ?: ""
            val categoria = document.getString("categoria") ?: ""
            val familia = document.getString("familia") ?: ""
            val modoDeUso = document.getString("modoDeUso") ?: ""
            val finalidades = document.getString("finalidades") ?: ""
            val partesUsadas = document.getString("partesUsadas") ?: ""
            val base64Image = document.getString("image") ?: ""
            PlantItem(plantId, descricao, nome, categoria, familia, modoDeUso, finalidades, partesUsadas, base64Image)
        } else {
            Log.w("Firestore", "Documento da planta não encontrado: $plantId")
            null // Retorna null se o documento não existir
        }
    } catch (e: Exception) {
        Log.e("Firestore", "Erro ao buscar planta: ${e.message}")
        null // Em caso de erro, retorna null
    }
}

// Função para converter a string Base64 em Bitmap
fun base64ToBitmap(base64: String): Bitmap? {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

@Composable
fun ImageDisplayScreen() {
    val coroutineScope = rememberCoroutineScope()
    var images by remember { mutableStateOf<List<ImageItem>>(emptyList()) }
    var selectedPlant by remember { mutableStateOf<PlantItem?>(null) }
    var loading by remember { mutableStateOf(false) }

    // Carrega as imagens ao iniciar
    LaunchedEffect(Unit) {
        loading = true
        images = fetchImagesFromFirestore()
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading) {
            Text(text = "Carregando imagens...", modifier = Modifier.padding(top = 16.dp))
        } else {
            // Lista de imagens
            LazyColumn {
                items(images) { imageItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch {
                                    selectedPlant = fetchPlantById(imageItem.id)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Renderiza a miniatura da imagem
                        val bitmap = base64ToBitmap(imageItem.base64)
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Imagem da planta",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                        Text(text = "Imagem ID: ${imageItem.id}")
                    }
                }
            }

            // Detalhes da planta selecionada
            selectedPlant?.let { plant ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Detalhes da Planta", style = MaterialTheme.typography.h6)

                // Renderiza a imagem completa da planta
                val plantBitmap = base64ToBitmap(plant.base64Image)
                plantBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Imagem completa da planta",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    )
                }

                // Detalhes textuais da planta
                Text(text = "Nome: ${plant.nome}")
                Text(text = "Descrição: ${plant.descricao}")
                Text(text = "Categoria: ${plant.categoria}")
                Text(text = "Família: ${plant.familia}")
                Text(text = "Modo de Uso: ${plant.modoDeUso}")
                Text(text = "Finalidades: ${plant.finalidades}")
                Text(text = "Partes Usadas: ${plant.partesUsadas}")
            }
        }
    }
}