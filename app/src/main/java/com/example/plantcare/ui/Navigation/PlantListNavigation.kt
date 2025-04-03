package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.screens.PlantListScreen
import com.example.plantcare.ui.states.PlantListUiState
import com.example.plantcare.ui.viewmodels.PlantListViewModel
import org.koin.androidx.compose.koinViewModel

const val plantListRoute = "plantList"

fun NavGraphBuilder.plantListScreen(
    onNavigateToNewPlantForm: () -> Unit,
    onNavigateToEditPlantForm: (Plant) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable(plantListRoute) {
        val viewModel = koinViewModel<PlantListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(PlantListUiState())
        PlantListScreen(
            uiState = uiState,
            onNewPlantClick = onNavigateToNewPlantForm,
            onPlantClick = onNavigateToEditPlantForm
        )
    }
}

fun NavHostController.navigateToPlantList() {
    navigate(plantListRoute)
}