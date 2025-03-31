package com.example.plantcare.ui.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.plantcare.Authentication.FirebaseAuthRepository
import com.example.plantcare.ui.screens.PlantCareScreen
import com.example.plantcare.ui.viewmodels.PlantCareViewModel
import org.koin.androidx.compose.koinViewModel

const val plantCareGraphRoute = "plantCareGraph"

fun NavGraphBuilder.plantCareScreen(
    navController: NavController,
    onNavigateToLogin: () -> Unit
){
    composable(plantCareGraphRoute){
        val viewModel = koinViewModel<PlantCareViewModel>()
        PlantCareScreen(
            navController = navController,
            onExitToAppClick = {
                viewModel.signOut()
                onNavigateToLogin()
            }
        )
    }
}

fun NavHostController.navigateToPlantCare() {
    navigate(plantCareGraphRoute)
}