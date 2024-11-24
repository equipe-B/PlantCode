import com.google.firebase.firestore.FirebaseFirestore
import com.seuapp.data.model.Administrador

class AdministradorRepository {

    private val db = FirebaseFirestore.getInstance()

    fun adicionarAdministrador(
        id: String,
        administrador: Administrador,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("administradores")
            .document(id)
            .set(administrador)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}