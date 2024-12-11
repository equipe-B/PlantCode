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
            startDestination = "access_type", // Tela inicial
            modifier = Modifier.fillMaxSize()
        ) {
            composable("access_type") {
                AccessTypeScreen(
                    navigateToAdminLogin = { navController.navigate("login") },
                    navigateToVisitorLogin = { navController.navigate("login") }
                )
            }

            composable("login") {
                LoginScreen(
                    navigateToRegister = { navController.navigate("register") },
                    navigateToForgotPassword = { navController.navigate("forgot_password") },
                    navigateToHome = { navController.navigate("home") },
                    navigateBackToAccessType = { navController.popBackStack("access_type", false) }
                )
            }

            composable("register") {
                RegisterScreen(
                    navigateBackToLogin = { navController.popBackStack() },
                    navigateToHome = { navController.navigate("home") }
                )
            }

            composable("forgot_password") {
                ForgotPasswordScreen(
                    navigateBackToLogin = { navController.popBackStack() }
                )
            }

            composable("home") {
                HomeScreen(
                    navigateToCategories = { navController.navigate("categories") },
                    navigateToQRCodeScanner = { navController.navigate("qr_code_scanner") },
                    navigateToAddPlant = { navController.navigate("add_plant") },
                    navigateToSettings = { navController.navigate("settings") },
                    navigateToPlantasDeCura = { navController.navigate("plantas_de_cura") },
                    navigateToPlantasDeProtecao = { navController.navigate("plantas_de_protecao") }
                )
            }

            composable("categories") {
                CategoriesScreen(
                    navigateToPlantasDeCura = { navController.navigate("plantas_de_cura") },
                    navigateToPlantasDeProtecao = { navController.navigate("plantas_de_protecao") },
                    navigateToAddPlant = { navController.navigate("add_plant") },
                    navigateToQRCodeScanner = { navController.navigate("qr_code_scanner") },
                    navigateToSettings = { navController.navigate("settings") },
                    navigateToHome = { navController.navigate("home") }
                )
            }

            composable("plantas_de_cura") {
                PlantasDeCuraScreen(
                    navigateBackToCategories = { navController.popBackStack("categories", false) },
                    navigateToHome = { navController.navigate("home") },
                    navigateToQRCodeScanner = { navController.navigate("qr_code_scanner") },
                    navigateToAddPlant = { navController.navigate("add_plant") },
                    navigateToSettings = { navController.navigate("settings") }
                )
            }

            composable("plantas_de_protecao") {
                PlantasDeProtecaoScreen(
                    navigateBackToCategories = { navController.popBackStack("categories", false) },
                    navigateToHome = { navController.navigate("home") },
                    navigateToQRCodeScanner = { navController.navigate("qr_code_scanner") },
                    navigateToAddPlant = { navController.navigate("add_plant") },
                    navigateToSettings = { navController.navigate("settings") }
                )
            }

            composable("settings") {
                SettingsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToUserProfile = { navController.navigate("profile") } // Adicionado para navegação ao perfil do usuário
                )
            }

            composable("add_plant") {
                AddPlantScreen(
                    navigateToHome = { navController.navigate("home") },
                    navigateToQRCodeScanner = { navController.navigate("qr_code_scanner") },
                    navigateToAddPlant = { /* Permanece na mesma tela */ },
                    navigateToSettings = { navController.navigate("settings") }
                )
            }

            composable("qr_code_scanner") {
                QRCodeScannerScreen(
                    onScanResult = { result ->
                        println("QR Code Scan Result: $result")
                    }
                )
            }

            composable("profile") {
                UserProfileScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
