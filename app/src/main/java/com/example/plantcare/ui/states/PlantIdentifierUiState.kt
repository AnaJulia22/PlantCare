package com.example.plantcare.ui.states

import com.example.plantcare.data.remote.dto.Suggestion

data class PlantIdentifierUiState(
    val plantName: String = "",
    val plantImage: String = "",
    val probability: Double = 0.0,
    val isLoading: Boolean = false,
    val description: String = "",
    val commonNames: List<String> = emptyList(),
    val toxicity: String = "",
    val plantUrl: String = "",
    val bestWatering: String = "",
    val suggestions: List<Suggestion> = emptyList(),
    val errorMessage: String? = null
)