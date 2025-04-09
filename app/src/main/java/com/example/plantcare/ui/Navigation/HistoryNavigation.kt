package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.models.PlantSuggestion
import com.example.plantcare.ui.screens.HistoryScreen
import com.example.plantcare.ui.states.PlantSuggestionListUiState
import com.example.plantcare.ui.viewmodels.PlantSuggestionListViewModel
import org.koin.androidx.compose.koinViewModel

const val historyRoute = "history"

fun NavGraphBuilder.historyScreen(
    navController: NavController,
    onNavigateToNewPlantIdentifierForm: () -> Unit,
    onPlantSuggestionClick: (PlantSuggestion) -> Unit,
    onNavigateToLogin: () -> Unit,

    ) {
    composable(historyRoute) {
        val viewModel = koinViewModel<PlantSuggestionListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(PlantSuggestionListUiState())
        HistoryScreen(
            uiState = uiState,
            navController = navController,
            onNewRequestClick = onNavigateToNewPlantIdentifierForm,
            onExitToAppClick = {
                viewModel.signOut()
                onNavigateToLogin()
            },
            onPlantSuggestionClick = onPlantSuggestionClick
        )
    }
}

fun NavHostController.navigateTohistory() {
    navigate(historyRoute)
}
