package com.example.plantcare.models

data class PlantSuggestion(
    val accessToken: String,
    val identifiedAt: Long = System.currentTimeMillis(),
    val name: String?,
    val probability: Double?,
    val image: String?,
    val common_names: String?,
    val url: String?,
    val descriptionValue: String?,
    val best_watering: String?,
    val toxicity: String?,
    val imageValue: String?
)