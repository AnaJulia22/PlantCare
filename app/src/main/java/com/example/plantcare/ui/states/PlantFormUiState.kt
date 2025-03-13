package com.example.plantcare.ui.states

data class PlantFormUiState (
    var name: String = "",
    var species: String? = "",
    var wateringFrequency: String = "",
    var isWatered: Boolean = false,
    var lastWatered: String? = "",
    var nextWatering: String = "",
    var imageRes: Int? = 0,
    val topAppBarName: String = "",
    val onNameChange: (String) -> Unit = {},
    val onSpeciesChange: (String) -> Unit = {},
    val onWateringFrequencyChange: (String) -> Unit = {},
    val onIsWateredChange: (Boolean) -> Unit = {},
    val onLastWateredChange: (String) -> Unit = {},
    val onNextWateringChange: (String) -> Unit = {},
    val onImageResChange: (Int) -> Unit = {},
    val isDeleteEnabled: Boolean = false,
)