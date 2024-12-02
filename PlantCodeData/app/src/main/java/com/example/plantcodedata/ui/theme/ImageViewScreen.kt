package com.example.plantcodedata.ui.theme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Importando a função items corretamente
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Data class to represent an image item
data class ImageItem(val id: String, val base64: String)

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
        emptyList() // Em caso de erro, retorna uma lista vazia
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
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var loading by remember { mutableStateOf(false) }

    // Carrega as imagens ao iniciar
    LaunchedEffect(Unit) {
        loading = true
        images = fetchImagesFromFirestore() // Busca todas as imagens do Firestore
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
                items(images) { imageItem -> // Use items corretamente aqui
                    Text(
                        text = "Imagem ID: ${imageItem.id}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                selectedBitmap = base64ToBitmap(imageItem.base64) // Converte para Bitmap ao clicar
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe a imagem selecionada, se houver
        selectedBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(200.dp) // Define o tamanho da imagem
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageDisplayScreen() {
    ImageDisplayScreen()
}