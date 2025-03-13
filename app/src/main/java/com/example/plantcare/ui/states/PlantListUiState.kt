package com.example.plantcare.ui.states

import com.example.plantcare.models.Plant

data class PlantListUiState (
    val plants: List<Plant> = emptyList(),
    val onPlantWateredChange: (Plant) -> Unit = {},
)