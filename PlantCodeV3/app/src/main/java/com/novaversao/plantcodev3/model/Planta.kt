package com.novaversao.plantcodev3.model
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


data class Plantas(
    val imagemBase64: String, // Altere para o nome correto aqui
    val descricao: String,
    val nome: String,
    val categoria: String,
    val familia: String,
    val modo_de_uso: String,
    val finalidades: String,
    val partes_usadas: String

)
class PlantaRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun AdicionarPlanta(planta: Plantas, imagemBase64: String, callback: (String?) -> Unit) {
        // Cria uma cópia da planta com a imagem em Base64
        val plantaComImagem = planta.copy(imagemBase64 = imagemBase64)

        // Referência para a coleção de Plantas
        val plantasCollection = firestore.collection("Plantas")

        // Adiciona o documento
        plantasCollection
            .add(plantaComImagem)
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