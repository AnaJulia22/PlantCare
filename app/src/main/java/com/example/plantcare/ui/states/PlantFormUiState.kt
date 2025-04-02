package com.example.plantcare.ui.states

data class PlantFormUiState (
    var name: String = "",
    var species: String? = "",
    var wateringFrequency: String = "",
    var isWatered: Boolean = false,
    var lastWatered: String? = "",
    var nextWatering: Long = "".toLongOrNull() ?: 0L,
    var imageRes: Int? = 0,
    val timeToWater: String? = "",
    val topAppBarName: String = "",
    val onNameChange: (String) -> Unit = {},
    val onSpeciesChange: (String) -> Unit = {},
    val onWateringFrequencyChange: (String) -> Unit = {},
    val onIsWateredChange: (Boolean) -> Unit = {},
    val onLastWateredChange: (String) -> Unit = {},
    val onNextWateringChange: (Long) -> Unit = {},
    val onImageResChange: (Int) -> Unit = {},
    val onTimeResChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false,
)