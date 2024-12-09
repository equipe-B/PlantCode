package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import android.widget.Toast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantQRCodeScreen(
    plantName: String,
    qrCodeBase64: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val qrCodeBitmap = remember(qrCodeBase64) {
        // Decodifica a string Base64 em um Bitmap
        Base64.decode(qrCodeBase64, Base64.DEFAULT)
            .let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }

    // Função para salvar o QR Code na galeria
    val saveQRCodeToGallery: () -> Unit = {
        val displayName = "QRCode_${plantName}_${System.currentTimeMillis()}.png"
        val mimeType = "image/png"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            // Mostra mensagem de sucesso
            Toast.makeText(
                context,
                "QR Code salvo na galeria",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do QR Code") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = plantName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Exibe o QR Code
            qrCodeBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code da Planta",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(16.dp)
                )
            }

            // Botão para salvar QR Code
            Button(
                onClick = saveQRCodeToGallery,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2AD23B)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Salvar QR Code")
            }
        }
    }
}