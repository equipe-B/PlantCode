package com.example.plantcodedata.model
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa o Firebase
        FirebaseApp.initializeApp(this)
    }
}


data class Administrador(
    val id: String? = null, // O ID será gerado automaticamente pelo Firestore
    val nome: String,
    val email: String,
    val senha: String // Usando um inteiro para o ano de nascimento, você pode mudar para String ou outra estrutura se preferir
)

class AdministradorRepository {
    // Referência para o Firestore
    private val firestore: FirebaseFirestore

    // Bloco de inicialização
    init {
        // Garantir que o Firebase está inicializado
        try {
            FirebaseApp.getInstance()
        } catch (e: IllegalStateException) {
            // Se não estiver inicializado, inicializa
            MyApplication().onCreate()
        }

        // Obtém a instância do Firestore
        firestore = Firebase.firestore
    }

    // Função para adicionar administrador
    fun adicionarAdministrador(administrador: Administrador, callback: (String?) -> Unit) {
        // Referência para a coleção de administradores
        val administradoresCollection = firestore.collection("administradores")

        // Adiciona o documento
        administradoresCollection
            .add(administrador)
            .addOnSuccessListener { documentReference ->
                // Sucesso na adição
                Log.d("AdministradorRepository", "Administrador adicionado com ID: ${documentReference.id}")
                callback(documentReference.id)
            }
            .addOnFailureListener { exception ->
                // Falha na adição
                Log.e("AdministradorRepository", "Erro ao adicionar administrador", exception)
                callback(null)
            }
    }

    // Você pode adicionar mais métodos como:
    fun buscarAdministrador(id: String, callback: (Administrador?) -> Unit) {
        firestore.collection("administradores")
            .document(id)
            .get()
            .addOnSuccessListener { documento ->
                val administrador = documento.toObject(Administrador::class.java)
                callback(administrador)
            }
            .addOnFailureListener { exception ->
                Log.e("AdministradorRepository", "Erro ao buscar administrador", exception)
                callback(null)
            }
    }
}