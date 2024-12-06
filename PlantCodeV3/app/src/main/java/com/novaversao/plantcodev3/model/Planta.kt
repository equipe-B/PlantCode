package com.novaversao.plantcodev3.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.util.Base64
import android.widget.Toast

data class Plantas(
    val imagemBase64: String, // Imagem da planta em Base64
    val descricao: String,
    val nome: String,
    val categoria: String,
    val familia: String,
    val modo_de_uso: String,
    val finalidades: String,
    val partes_usadas: String,
    val qrcodeBase64: String // Adicionado para armazenar o QR Code em Base64
)


class PlantaRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Trata-se da função de adicionar a planta ao repositório no Firestore Database
    fun AdicionarPlanta(planta: Plantas, imagemBase64: String, qrcodeBase64: String, callback: (String?) -> Unit) {

        // Cria uma cópia da planta com a imagem e o QR Code em Base64
        val plantaComImagemEQRCode = planta.copy(imagemBase64 = imagemBase64, qrcodeBase64 = qrcodeBase64)

        // Referência para a coleção de Plantas
        val plantasCollection = firestore.collection("Plantas")

        // Adiciona o documento
        plantasCollection
            .add(plantaComImagemEQRCode)
            .addOnSuccessListener { documentReference ->
                // Sucesso na adição
                Log.d("PlantaRepository", "Planta adicionada com ID: ${documentReference.id}")
                callback(documentReference.id)
            }
            .addOnFailureListener { exception ->
                // Falha na adição
                Log.e("PlantaRepository", "Erro ao adicionar Planta", exception)
                callback(null)
            }
    }
}


//Módulo criado para comprimir a função de converter a imagem em base64 e salvar no firestore database.
// Também possui a tratativa para evitar que o usuário insira uma imagem com o tamanho acima de 1 MB.
fun handleImageConversionAndSave(
    base64Image: String,
    base64QRCode: String, // Novo parâmetro para o QR Code
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

    // Verifica o tamanho do QR Code em Base64
    val qrCodeSizeInBytes = Base64.decode(base64QRCode, Base64.DEFAULT).size

    // Verifica se o tamanho do QR Code excede 1 MB
    if (qrCodeSizeInBytes > 1048576) { // 1 MB = 1048576 bytes
        // Log e mensagem se o QR Code exceder 1 MB
        Log.w("AdicionarPlanta", "O QR Code excede 1 MB. Tamanho: ${qrCodeSizeInBytes} bytes")
        Toast.makeText(context, "O QR Code deve ser menor que 1 MB. Selecione outro QR Code.", Toast.LENGTH_LONG).show()
        return // Retorna sem adicionar a planta
    }

    // Chama a função AdicionarPlanta com um callback
    plantaRepository.AdicionarPlanta(novaPlanta, base64Image, base64QRCode) { plantaId ->
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