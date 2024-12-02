package com.novaversao.plantcodev3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun QRCodeScannerScreen(
    onScanResult: (String) -> Unit // Callback para lidar com o resultado do QR Code
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Exemplo de placeholder para o scanner
        Text(text = "Scanner de QR Code") // Placeholder para a tela
    }
}

@Composable
fun QRCodeScannerPreview() {
    QRCodeScannerScreen(
        onScanResult = { result ->
            println("QR Code Scanned: $result")
        }
    )
}
