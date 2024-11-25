package com.example.plantcodedata.model
import com.google.firebase.firestore.FirebaseFirestore

data class Administrador(
    val id: String? = null, // O ID será gerado automaticamente pelo Firestore
    val nome: String,
    val email: String,
    val senha: String // Usando um inteiro para o ano de nascimento, você pode mudar para String ou outra estrutura se preferir
)

class AdministradorRepository {
    private val db = FirebaseFirestore.getInstance()

    fun adicionarAdministrador(administrador: Administrador, onComplete: (String?) -> Unit) {
        // Usando um HashMap para representar o administrador
        val adminData = hashMapOf(
            "nome" to administrador.nome,
            "email" to administrador.email,
            "senha" to administrador.senha
        )

        // Adicionando o documento à coleção "administradores"
        db.collection("administradores")
            .add(adminData)
            .addOnSuccessListener { documentReference ->
                onComplete(documentReference.id) // Retorna o ID do novo administrador
            }
            .addOnFailureListener { e ->
                onComplete(null) // Retorna null em caso de erro
                println("Erro ao adicionar administrador: $e")
            }
    }
}