
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class Administrador(
    val nome: String =  "",
    val email: String = "",
    val senha: String = ""
)



// Função para adicionar um novo administrador
fun adicionarAdministrador(nome: String, email: String, senha: String, nivelAcesso: String) {
    val db = FirebaseFirestore.getInstance()
    val novoAdministrador = Administrador(
        nome = nome,
        email = email,
        senha = senha,
    )

    db.collection("Administradores")
        .add(novoAdministrador)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Administrador adicionado com ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Erro ao adicionar administrador", e)
        }
}