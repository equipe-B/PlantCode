package com.novaversao.plantcodev3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.app_background), // Nome do plano de fundo
            contentDescription = "Plano de fundo",
            contentScale = ContentScale.Crop, // Ajusta a imagem ao tamanho da tela
            modifier = Modifier.fillMaxSize()
        )

        // Conteúdo sobreposto
        content()
    }
}
