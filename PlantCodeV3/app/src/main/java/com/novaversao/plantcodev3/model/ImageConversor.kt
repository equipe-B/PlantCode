package com.novaversao.plantcodev3.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.util.Base64
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

fun storeImageInFirestore(base64Image: String, callback: (String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val imageData = hashMapOf("image" to base64Image)

    db.collection("images")
        .add(imageData)
        .addOnSuccessListener { documentReference ->
            callback(documentReference.id)
        }
        .addOnFailureListener { e ->
            callback(null)
        }
}

fun converterBitmapParaBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

// Função para converter a imagem em Base64 com redimensionamento

fun convertImageToBase64(context: Context, uri: Uri): String {
    return try {
        // Carregar a imagem como Bitmap
        val inputStream = context.contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        // Redimensionar a imagem (opcional)
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 800, 800, true) // Redimensiona para 800x800

        // Converter o Bitmap para um ByteArray
        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Converter o ByteArray para Base64
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        Log.e("ImageConversion", "Erro ao converter imagem", e)
        ""
    }
}