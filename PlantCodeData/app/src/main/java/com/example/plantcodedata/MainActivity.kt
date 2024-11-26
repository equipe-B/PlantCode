package com.example.plantcodedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.plantcodedata.model.AdministradorRepository
import com.example.plantcodedata.model.Administrador
import com.example.plantcodedata.ui.theme.SeuPacoteTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var administradorRepository: AdministradorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialização do administradorRepository
        administradorRepository = AdministradorRepository()

        enableEdgeToEdge()

        setContent {
            SeuPacoteTheme {
                // Chama a função que define a interface
                MainScreen()

                // Criando um novo administrador
                val novoAdministrador = Administrador(
                    nome = "Ada Lovelace",
                    email = "ada.lovelace@example.com",
                    senha = "1815"
                )

                // Adicionando o administrador ao Firestore
                administradorRepository.adicionarAdministrador(novoAdministrador) { id ->
                    if (id != null) {
                        Log.d(TAG, "Administrador adicionado com ID: $id")
                    } else {
                        Log.w(TAG, "Falha ao adicionar administrador.")
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text(text = "Bem-vindo ao App!", style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    SeuPacoteTheme {
        MainScreen()
    }
}
