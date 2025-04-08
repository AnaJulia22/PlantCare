package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.plantcare.Application.WorkScheduler
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.screens.PlantFormScreen
import com.example.plantcare.ui.viewmodels.PlantFormViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val plantFormRoute = "plantForm"
const val plantIdArgument = "plantId"

fun NavGraphBuilder.plantFormScreen(
    onPopBackStack: () -> Unit,
) {
    composable("$plantFormRoute?$plantIdArgument={$plantIdArgument}") {
        val plantId = navArgument(plantIdArgument) {
            nullable = true
        }
        val scope = rememberCoroutineScope()
        val viewModel = koinViewModel<PlantFormViewModel>(
            parameters = { parametersOf(plantId) })
        val uiState by viewModel.uiState.collectAsState()
        PlantFormScreen(
            uiState = uiState,
            onSaveClick = {
                scope.launch {
                    viewModel.save()
                    onPopBackStack()
                }
            },
            onCancelClick = onPopBackStack,
            onDeleteClick = {
                scope.launch {
                    viewModel.delete()
                    onPopBackStack()
                }
            })
    }
}

fun NavHostController.navigateToNewPlantForm() {
    navigate(plantFormRoute)
}

fun NavHostController.navigateToEditPlantForm(plant: Plant) {
    navigate("$plantFormRoute?$plantIdArgument=${plant.id}")
}