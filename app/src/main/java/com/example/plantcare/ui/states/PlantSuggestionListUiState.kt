package com.example.plantcare.ui.states

import com.example.plantcare.models.PlantSuggestion

data class PlantSuggestionListUiState(
    val plantSuggestion: List<PlantSuggestion> = emptyList()
)