package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.PlantIdentifierScreen
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

const val plantIdentifierRoute = "plant_identifier"

fun NavGraphBuilder.plantIdentifierScreen(
    onNavigateToCamera: () -> Unit
) {
    composable(plantIdentifierRoute) {
        val viewModel = koinViewModel<PlantIdentifierViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(PlantIdentifierUiState())
        PlantIdentifierScreen(
            uiState = uiState,
            onImageSelected = viewModel::identify,
            onOpenCamera = onNavigateToCamera
        )
    }
}

fun NavHostController.navigateToPlantIdentifier() {
    navigate(plantIdentifierRoute)
}