package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.CameraScreen
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import com.example.plantcare.ui.viewmodels.PlantResultViewModel
import org.koin.androidx.compose.koinViewModel

const val cameraCaptureRoute = "camera"
fun NavGraphBuilder.cameraScreen (
    onNavigateToResult: () -> Unit
) {
    composable(cameraCaptureRoute) {
        val viewModel = koinViewModel<PlantIdentifierViewModel>()
        val resultViewModel = koinViewModel<PlantResultViewModel>()
        val uiState by viewModel.uiState.collectAsState()


        CameraScreen(
            viewModel = viewModel,
            onNavigateToResult = onNavigateToResult
        )

        // Se identificou a planta e ainda não navegou
        if (uiState.plantName != null && !uiState.isLoading && uiState.errorMessage == null) {
            LaunchedEffect(uiState.plantName) {
                resultViewModel.setResult(uiState)
                onNavigateToResult()
            }
        }
    }
}

fun NavHostController.navigateToCamera() {
    navigate(cameraCaptureRoute)
}