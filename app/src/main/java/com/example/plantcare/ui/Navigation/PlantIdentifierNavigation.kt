package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.PlantIdentifierScreen
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import com.example.plantcare.ui.viewmodels.PlantResultViewModel
import org.koin.androidx.compose.koinViewModel

const val plantIdentifierRoute = "plant_identifier"

fun NavGraphBuilder.plantIdentifierScreen(
    onNavigateToCamera: () -> Unit,
    onNavigateToResult: () -> Unit
) {
    composable(plantIdentifierRoute) {entry ->
        val viewModel = koinViewModel<PlantIdentifierViewModel>(
            viewModelStoreOwner = entry
        )
        //val viewModel = koinViewModel<PlantIdentifierViewModel>()
        //val uiState by viewModel.uiState.collectAsState()

        PlantIdentifierScreen(
            viewModel = viewModel,
            onImageSelected = viewModel::identify,
            onOpenCamera = onNavigateToCamera,
            onNavigateToResult = onNavigateToResult
        )
    }
}

fun NavHostController.navigateToPlantIdentifier() {
    navigate(plantIdentifierRoute)
}