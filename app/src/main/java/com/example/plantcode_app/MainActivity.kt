import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seuapp.data.model.Administrador
import com.seuapp.data.model.Permissoes
import com.seuapp.data.repository.AdministradorRepository

class MainActivity : AppCompatActivity() {

    private val administradorRepository = AdministradorRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissoes = Permissoes(gerenciarUsuarios = true, acessoAnalitico = true)
        val novoAdministrador = Administrador(
            nome = "Carlos Pereira",
            email = "carlos.pereira@example.com",
            dataCriacao = "2024-01-01",
            permissoes = permissoes
        )

        adicionarAdministrador("admin3", novoAdministrador)
    }

    private fun adicionarAdministrador(id: String, administrador: Administrador) {
        administradorRepository.adicionarAdministrador(id, administrador,
            onSuccess = {
                println("Administrador adicionado com sucesso!")
            },
            onFailure = { e ->
                println("Erro ao adicionar administrador: ${e.message}")
            }
        )
    }
}