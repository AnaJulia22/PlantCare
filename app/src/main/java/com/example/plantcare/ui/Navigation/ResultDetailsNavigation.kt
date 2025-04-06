package com.example.plantcare.ui.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.PlantDetailsScreen
import com.example.plantcare.ui.states.PlantIdentifierUiState

const val plantResultDetailsRoute = "plant_details"

fun NavGraphBuilder.plantResultDetailsScreen(
) {
    composable(plantResultDetailsRoute) {
        PlantDetailsScreen()
    }
}

fun NavHostController.navigateToPlantResultDetails() {
    navigate(plantResultDetailsRoute)
}