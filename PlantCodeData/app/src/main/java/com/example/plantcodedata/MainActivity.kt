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
import com.example.plantcodedata.model.PlantaRepository
import com.example.plantcodedata.model.Plantas
import com.example.plantcodedata.ui.theme.SeuPacoteTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var PlantaRepository: PlantaRepository
    private lateinit var administradorRepository: AdministradorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialização do administradorRepository
        administradorRepository = AdministradorRepository()
        PlantaRepository = PlantaRepository()

        enableEdgeToEdge()

        setContent {
            SeuPacoteTheme {
                // Chama a função que define a interface
                MainScreen()

                // Criando um novo administrador
                val novoAdministrador = Administrador(
                    nome = "Douglas",
                    email = "Douglas.Cruz@example.com",
                    senha = "AGARAGAN"
                )

                // Adicionando o administrador ao Firestore
                administradorRepository.adicionarAdministrador(novoAdministrador) { id ->
                    if (id != null) {
                        Log.d(TAG, "Administrador adicionado com ID: $id")
                    } else {
                        Log.w(TAG, "Falha ao adicionar administrador.")
                    }
                }
                val novaPlanta = Plantas(
                    foto = "https://www.google.com",
                    nome = "Cipó-alho",
                    descricao = "O cipó-alho (Mansoa alliacea) é uma trepadeira perenifólia, de pequeno porte, da família das Bignoniáceas, a mesma família dos Ipês. Ela apresenta florescimento ornamental, além de interessantes qualidades condimentares e medicinais",
                    familia = "Bignoniaceae" ,
                    modo_de_uso = "Banho",
                    finalidades = "Moleza e Olho Grande",
                    partes_usadas= "Completa"
                )
                //Adicionando Planta ao Firestore
                PlantaRepository.AdicionarPlanta(novaPlanta){id ->
                    if (id != null) {
                        Log.d(TAG, "Planta adicionada com ID: $id")
                    } else{
                        Log.w(TAG, "Falha ao adicionar planta.")
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
