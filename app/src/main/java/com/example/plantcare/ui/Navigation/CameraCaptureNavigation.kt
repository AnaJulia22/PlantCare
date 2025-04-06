package com.example.plantcare.ui.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.CameraScreen
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

const val cameraCaptureRoute = "camera"
fun NavGraphBuilder.cameraScreen (
    onNavigateToResult: () -> Unit
) {
    composable(cameraCaptureRoute) {
        val viewModel = koinViewModel<PlantIdentifierViewModel>()
        CameraScreen(
            viewModel = viewModel,
            onNavigateToResult = onNavigateToResult
        )
    }
}

fun NavHostController.navigateToCamera() {
    navigate(cameraCaptureRoute)
}