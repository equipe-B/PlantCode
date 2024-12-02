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
import com.example.plantcodedata.model.Plantas
import com.example.plantcodedata.model.PlantaRepository
@Composable
fun ImageUploadScreen() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadSuccess by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        uploadSuccess = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Selecionar Imagem")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                imageUri?.let { uri ->
                    val base64Image = convertImageToBase64(context, uri)
                    storeImageInFirestore(base64Image) { imageId ->
                        if (imageId != null) {
                            val novaPlanta = Plantas(
                                descricao = "Descrição da planta",
                                nome = "Nome da planta",
                                categoria = "Categoria da planta",
                                familia = "Família da planta",
                                modo_de_uso = "Modo de uso",
                                finalidades = "Finalidades",
                                partes_usadas = "Partes usadas",
                                imageId = imageId
                            )

                            val plantaRepository = PlantaRepository()
                            plantaRepository.AdicionarPlanta(novaPlanta) { plantaId ->
                                uploadSuccess = plantaId != null
                            }
                        } else {
                            // Tratar erro ao armazenar imagem
                            uploadSuccess = false
                        }
                    }
                }
            },
            enabled = imageUri != null
        ) {
            Text(text = "Upload")
        }

        if (uploadSuccess) {
            Text(text = "Upload realizado com sucesso!", color = androidx.compose.ui.graphics.Color.Green)
        } else if (imageUri != null) {
            Text(text = "Erro ao realizar upload.", color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}

fun convertImageToBase64(context: Context, uri: Uri): String {
    val contentResolver = context.contentResolver
    val bitmap: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun storeImageInFirestore(base64Image: String, callback: (String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val imageData = hashMapOf("image" to base64Image)

    db.collection("images")
        .add(imageData)
        .addOnSuccessListener { documentReference ->
            callback(documentReference.id)
        }
        .addOnFailureListener { e ->
            callback(null)
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageUploadScreen() {
    ImageUploadScreen() // Chama a função que exibe a tela de upload de imagem
}