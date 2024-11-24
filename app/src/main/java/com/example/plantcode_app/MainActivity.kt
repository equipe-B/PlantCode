import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seuapp.modelo.adicionarAdministrador // Importe a função

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Chame a função para adicionar um novo administrador para teste
        adicionarAdministrador("Admin Teste", "admin.teste@example.com", "senhaSegura", "Admin")
    }
}