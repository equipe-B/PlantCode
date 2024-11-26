package com.example.plantcodedata.model

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.security.auth.callback.Callback

data class Plantas(
    val foto: String = "", //URL da Imagem
    val nome: String = "",
    val descricao: String = "",
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

    }
