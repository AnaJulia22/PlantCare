package com.example.plantcare.ui.Navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.CameraScreen
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

const val cameraCaptureRoute = "camera"
fun NavGraphBuilder.cameraScreen (
    navController: NavController,
    onNavigateToResult: () -> Unit
) {
    composable(cameraCaptureRoute) {entry ->
        val parentEntry = remember(entry) {
            navController.getBackStackEntry(plantIdentifierRoute)
        }
        val viewModel: PlantIdentifierViewModel = koinViewModel(
            viewModelStoreOwner = parentEntry
        )

        CameraScreen(
            viewModel = viewModel,
            onImageCaptured = { uri ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("capturedImageUri", uri)

            },
            onNavigateToResult = onNavigateToResult
        )
    }
}

fun NavHostController.navigateToCamera() {
    Log.d("NAVIGATION", "Navigating to camera screen")
    navigate(cameraCaptureRoute)
}