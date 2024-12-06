package com.novaversao.plantcodev3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


class QrCodeScannerActivity : ComponentActivity() {
    private var qrCodeResult by mutableStateOf<String?>(null)
    var generatedQrCode by mutableStateOf<Bitmap?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    QrCodeScannerScreen(
                        onScanRequested = { initiateQrCodeScan() },
                        onGenerateQrRequested = { generateQRCode("Exemplo de QR Code") }
                    )
                }
            }
        }
    }

    private fun generateQRCode(content: String, width: Int = 300, height: Int = 300) {
        try {
            val bitMatrix = QRCodeWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                width,
                height
            )

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                    )
                }
            }

            generatedQrCode = bitmap
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initiateQrCodeScan() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val integrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                integrator.setPrompt("")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(false)
                integrator.setBarcodeImageEnabled(false)
                integrator.setOrientationLocked(false)
                integrator.initiateScan()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            initiateQrCodeScan()
        } else {
            Toast.makeText(this, "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                qrCodeResult = result.contents
                Toast.makeText(this, "Resultado: ${result.contents}", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}


@Composable
fun QrCodeScannerScreen(
    onScanRequested: () -> Unit,
    onGenerateQrRequested: () -> Unit
) {
    val context = LocalContext.current
    var showGeneratedQrCode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onScanRequested) {
            Text("Escanear QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onGenerateQrRequested()
            showGeneratedQrCode = true
        }) {
            Text("Gerar QR Code")
        }

        // Verifica se o QR Code foi gerado
        (context as? QrCodeScannerActivity)?.generatedQrCode?.let { bitmap ->
            if (showGeneratedQrCode) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code Gerado",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}