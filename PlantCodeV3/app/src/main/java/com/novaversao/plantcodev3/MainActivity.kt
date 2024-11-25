package com.novaversao.plantcodev3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.novaversao.plantcodev3.ui.theme.PlantCodeV3Theme

// Importação necessária para o componente Text
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantCodeV3Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "login", // Tela inicial do aplicativo
            modifier = Modifier.fillMaxSize()
        ) {
            // Rota para a Tela de Login
            composable("login") {
                LoginScreen(
                    navigateToRegister = {
                        navController.navigate("register") // Navega para a tela de Cadastro
                    },
                    navigateToForgotPassword = {
                        navController.navigate("forgot_password") // Navega para a tela de Recuperação de Senha
                    },
                    navigateToHome = {
                        navController.navigate("home") // Navega para a tela Inicial
                    }
                )
            }

            // Rota para a Tela de Cadastro
            composable("register") {
                RegisterScreen(
                    navigateBackToLogin = {
                        navController.popBackStack() // Volta para a tela de Login
                    }
                )
            }

            // Rota para a Tela de Recuperação de Senha
            composable("forgot_password") {
                ForgotPasswordScreen(
                    navigateBackToLogin = {
                        navController.popBackStack() // Volta para a tela de Login
                    }
                )
            }

            // Rota para a Tela Inicial (Home)
            composable("home") {
                HomeScreen(
                    navigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    navigateToCategories = {
                        navController.navigate("categories") // Navega para a tela de Categorias
                    },
                    navigateToSettings = {
                        navController.navigate("settings") // Navega para a tela de Configurações
                    }
                )
            }

            // Rota para a Tela de Categorias
            composable("categories") {
                CategoriesScreen(
                    navigateToPlantasDeCura = {
                        navController.navigate("plantas_de_cura") // Navega para a tela de Plantas de Cura
                    },
                    navigateToPlantasDeProtecao = {
                        navController.navigate("plantas_de_protecao") // Navega para a tela de Plantas de Proteção
                    },
                    navigateToAddPlant = {
                        navController.navigate("add_plant") // Navega para a tela de Adicionar Planta
                    },
                    navigateToQRCodeScanner = {
                        navController.navigate("qr_code_scanner") // Navega para a tela de Leitura de QR Code
                    },
                    navigateToSettings = {
                        navController.navigate("settings") // Navega para a tela de Configurações
                    }
                )
            }

            // Rota para a Tela de Plantas de Cura
            composable("plantas_de_cura") {
                PlantasDeCuraScreen(
                    navigateBackToCategories = {
                        navController.popBackStack("categories", false) // Volta para a tela de Categorias
                    }
                )
            }

            // Rota para a Tela de Plantas de Proteção
            composable("plantas_de_protecao") {
                PlantasDeProtecaoScreen(
                    navigateBackToCategories = {
                        navController.popBackStack("categories", false) // Volta para a tela de Categorias
                    }
                )
            }

            // Rota para a Tela de Detalhes da Planta
            composable("plant_details") {
                PlantDetailsScreen(
                    navigateBack = { navController.popBackStack() } // Volta para a tela anterior
                )
            }

            // Rota para a Tela de Adicionar Planta
            composable("add_plant") {
                AddPlantScreen(
                    navigateBack = { navController.popBackStack() } // Volta para a tela anterior
                )
            }

            // Rota para a Tela de Leitura de QR Code
            composable("qr_code_scanner") {
                QRCodeScannerScreen(
                    onScanResult = { result ->
                        println("QR Code lido: $result")
                    }
                )
            }

            // Rota para a Tela de Configurações
            composable("settings") {
                SettingsScreen(
                    navigateBack = { navController.popBackStack() } // Volta para a tela anterior
                )
            }
        }
    }
}
