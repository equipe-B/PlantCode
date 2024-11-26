package com.example.plantcodedata.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


data class Plantas(
    val foto: String = "", //URL da Imagem
    val descricao: String = "",
    val nome: String = "",
    val categoria: String = "",
    val familia: String = " " ,
    val modo_de_uso: String = "",
    val finalidades: String = "",
    val partes_usadas: String = ""
)

class PlantaRepository{
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun AdicionarPlanta(planta: Plantas,  callback: (String?) -> Unit){
        //Referência para a coleção de Plantas
        val PlantasCollection = firestore.collection("Plantas")
        // Adiciona o documento
        PlantasCollection
            .add(planta)
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
    fun BuscarPlanta(id: String, callback: (Plantas?) -> Unit) {
        val PlantasCollection = firestore.collection("Plantas").document(id)

        PlantasCollection.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("PlantaRepository", "Planta encontrada: ${document.id}")
                    val planta = document.toObject(Plantas::class.java)
                    callback(planta)
                } else {
                    Log.d("PlantaRepository", "Nenhuma planta encontrada com o ID: $id")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PlantaRepository", "Erro ao buscar planta", exception)
                callback(null)
            }
    }

    fun DeletarPlanta(id: String, callback: (Boolean) -> Unit) {
        val PlantasCollection = firestore.collection("Plantas").document(id)

        PlantasCollection.delete()
            .addOnSuccessListener {
                Log.d("PlantaRepository", "Planta deletada com ID: $id")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.e("PlantaRepository", "Erro ao deletar planta", exception)
                callback(false)
            }
    }

    fun EditarPlanta(id: String, plantaAtualizada: Plantas, callback: (Boolean) -> Unit) {
        val PlantasCollection = firestore.collection("Plantas").document(id)

        PlantasCollection.set(plantaAtualizada)
            .addOnSuccessListener {
                Log.d("PlantaRepository", "Planta editada com ID: $id")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.e("PlantaRepository", "Erro ao editar planta", exception)
                callback(false)
            }
    }
}
