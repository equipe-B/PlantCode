package com.novaversao.plantcodev3

import android.annotation.SuppressLint
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "access_type", // Tela inicial: Escolha do Tipo de Acesso
            modifier = Modifier.fillMaxSize()
        ) {
            // Rota para a Tela de Escolha do Tipo de Acesso
            composable("access_type") {
                AccessTypeScreen(
                    navigateToAdminLogin = {
                        navController.navigate("login") // Navega para a tela de Login (Administrador)
                    },
                    navigateToVisitorLogin = {
                        navController.navigate("login") // Navega para a tela de Login (Visitante)
                    }
                )
            }

            // Rota para a Tela de Login
            composable("login") {
                LoginScreenn(
                    onLoginSuccess = {
                        navController.navigate("home") {
                            // Limpa a pilha de navegação para que não seja possível voltar para o login
                            popUpTo("access_type") { inclusive = true }
                        }
                    },
                    navigateToRegister = {
                        navController.navigate("register")
                    },
                    navigateToForgotPassword = {
                        navController.navigate("forgot_password")
                    },
                    navigateBackToAccessType = {
                        navController.popBackStack("access_type", false)
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
                    },
                    navigateToUserProfile = {
                        navController.navigate("user_profile") // Navega para a tela de Perfil do Usuário
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

            // Rota para a Tela de Configurações
            composable("settings") {
                SettingsScreen(
                    navigateBack = { navController.popBackStack() } // Volta para a tela anterior
                )
            }

            // Rota para a Tela de Perfil do Usuário
            composable("user_profile") {
                UserProfileScreen(
                    navigateBack = { navController.popBackStack() } // Volta para a tela anterior
                )
            }

            // Outras rotas mantidas como antes...
        }
    }
}
