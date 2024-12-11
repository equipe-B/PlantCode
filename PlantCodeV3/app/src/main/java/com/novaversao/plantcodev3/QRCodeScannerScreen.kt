package com.novaversao.plantcodev3

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class ZXingCaptureActivity : CaptureActivity()

@Composable
fun QRCodeScannerScreen(navController: NavController) {
    val context = LocalContext.current
    var scannedResult by remember { mutableStateOf<String?>(null) }

    // Launcher para lidar com o resultado da captura do QR Code
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val scanResult = data?.getStringExtra("SCAN_RESULT")

            if (scanResult != null) {
                // QR Code foi scaneado com sucesso
                scannedResult = scanResult

                // Navegar para a página da planta usando o resultado do QR Code
                navController.navigate("plant_details/${scannedResult}")
            }
        }
    }

    // Função para iniciar o scanner de QR Code
    fun startQRCodeScanner() {
        val integrator = IntentIntegrator(context as Activity)
        integrator.setPrompt("Escaneie o QR Code da planta")
        integrator.setCameraId(0) // Câmera traseira
        integrator.setBeepEnabled(true) // Som ao escanear
        integrator.setBarcodeImageEnabled(true)
        integrator.setCaptureActivity(ZXingCaptureActivity::class.java)

        val scanIntent = integrator.createScanIntent()
        scanLauncher.launch(scanIntent)
    }

    // Chame startQRCodeScanner() quando quiser iniciar o scanner
    DisposableEffect(Unit) {
        startQRCodeScanner()
        onDispose {}
    }

    // Opcional: Mostrar o resultado do scan (para depuração)
    scannedResult?.let { result ->
        Text("QR Code escaneado: $result")
    }
}
