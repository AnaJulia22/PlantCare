package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.PlantDetailsScreen
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

const val plantResultDetailsRoute = "plant_details"

fun NavGraphBuilder.plantResultDetailsScreen(
    navController : NavHostController
) {
    composable(plantResultDetailsRoute) {entry ->
        val parentEntry = remember(entry) {
            navController.getBackStackEntry(plantIdentifierRoute)
        }

        val viewModel = koinViewModel<PlantIdentifierViewModel>(
            viewModelStoreOwner = parentEntry
        )
        PlantDetailsScreen(viewModel)
    }
}

fun NavHostController.navigateToPlantResultDetails() {
    navigate(plantResultDetailsRoute)
}