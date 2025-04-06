package com.example.plantcare.data.remote.dto

data class PlantRequest(
    val images: List<String>, // base64 da imagem
    val latitude: Double? = null,
    val longitude: Double? = null
)
