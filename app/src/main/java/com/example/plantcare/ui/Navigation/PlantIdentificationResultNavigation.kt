package com.example.plantcare.ui.Navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.PlantIdentificationResultScreen
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

const val plantResultRoute = "plant_result"

fun NavGraphBuilder.plantResultScreen(
    onNavigateToPlantDetails: () -> Unit,
    navController : NavHostController
) {
    composable(plantResultRoute) {entry ->
        // Pegando a instância da ViewModel associada ao "identificador"
        val parentEntry = remember(entry) {
            navController.getBackStackEntry(plantIdentifierRoute)
        }


        val viewModel = koinViewModel<PlantIdentifierViewModel>(
            viewModelStoreOwner = parentEntry
        )
        PlantIdentificationResultScreen(
            viewModel = viewModel,
            onDetailsClick = onNavigateToPlantDetails
        )

    }
}

fun NavHostController.navigateToPlantResult() {
    Log.d("NAVIGATION", "Navegando para resultados")
    navigate(plantResultRoute)
}