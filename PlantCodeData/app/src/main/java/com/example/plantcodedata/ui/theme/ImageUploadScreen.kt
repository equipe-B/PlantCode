package com.example.plantcodedata.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ImageUploadScreen()  {
    val context = LocalContext.current
    // Estado para armazenar a URI da imagem selecionada
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Estado para controlar se a imagem foi enviada
    var uploadSuccess by remember { mutableStateOf(false) }

    // Launcher para obter a imagem da galeria
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri // Atualiza a URI da imagem selecionada
        uploadSuccess = false // Reseta o estado de sucesso ao selecionar uma nova imagem
    }
    // Layout da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botão para selecionar uma imagem
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Selecionar Imagem") // Texto do botão
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os botões

        // Exibe a imagem selecionada, se houver
        imageUri?.let {
            Image(
                painter = rememberImagePainter(it), // Carrega a imagem usando Coil
                contentDescription = null, // Descrição do conteúdo (opcional)
                modifier = Modifier.size(200.dp) // Define o tamanho da imagem
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre a imagem e o botão de upload


        // Botão para fazer o upload da imagem
        Button(
            onClick = {
                imageUri?.let { uri ->
                    // Converte a imagem em Base64 e armazena no Firestore
                    val base64Image = convertImageToBase64(context, uri)
                    storeImageInFirestore(base64Image)
                    uploadSuccess = true // Marca que o upload foi iniciado
                }
            },
            enabled = imageUri != null // O botão só é habilitado se uma imagem foi selecionada
        ) {
            Text(text = "Upload") // Texto do botão de upload
        }

        // Mensagem de sucesso ao carregar a imagem
        if (uploadSuccess) {
            Text(text = "Imagem enviada com sucesso!", color = androidx.compose.ui.graphics.Color.Green)
        }
    }
}



// Função para converter uma imagem em Base64
fun convertImageToBase64(context: Context, uri: Uri): String {
    val contentResolver = context.contentResolver
    val bitmap: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


// Função para armazenar a imagem codificada no Firestore
fun storeImageInFirestore(base64Image: String) {
    val db = FirebaseFirestore.getInstance()
    val imageData = hashMapOf("image" to base64Image)

    db.collection("images")
        .add(imageData)
        .addOnSuccessListener { documentReference ->
            // Imagem armazenada com sucesso
        }
        .addOnFailureListener { e ->
            // Tratar erro ao armazenar
        }
}


@Preview(showBackground = true)
@Composable
fun PreviewImageUploadScreen() {
    ImageUploadScreen()
}