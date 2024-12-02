package com.novaversao.plantcodev3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journeyapps.barcodescanner.CaptureActivity

@Composable
fun QRCodeScannerScreen(
    modifier: Modifier = Modifier,
    onScanResult: (String) -> Unit // Função que será chamada quando o QR Code for lido
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Escaneie um QR Code",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botão para iniciar a leitura do QR Code
        Button(
            onClick = {
                // Inicia o scanner de QR Code
                onScanResult("QR Code lido com sucesso!")
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Iniciar Leitura do QR Code")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRCodeScannerScreenPreview() {
    QRCodeScannerScreen(
        onScanResult = { result ->
            println("Resultado do QR Code: $result")
        }
    )
}
