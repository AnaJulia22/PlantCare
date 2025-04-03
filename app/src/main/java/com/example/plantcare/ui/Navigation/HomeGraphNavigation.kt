package com.example.plantcare.ui.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.plantcare.models.Plant

const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    onNavigateToNewPlantForm: () -> Unit,
    onNavigateToEditPlantForm: (Plant) -> Unit,
    onPopBackStack: () -> Unit,
    navController: NavController,
    onNavigateToLogin: () -> Unit
) {
    navigation(
        //startDestination = plantListRoute,
        startDestination = plantCareGraphRoute,
        route = homeGraphRoute
    ) {
        plantCareScreen(
            navController,
            onNavigateToLogin = onNavigateToLogin
        )

        plantListScreen(
            onNavigateToNewPlantForm = onNavigateToNewPlantForm,
            onNavigateToEditPlantForm = onNavigateToEditPlantForm,
            onNavigateToLogin = onNavigateToLogin
        )
        plantFormScreen(onPopBackStack = onPopBackStack)
    }
}

fun NavHostController.navigateToHomeGraph(
    navOptions: NavOptions? = null
) {
    navigate(homeGraphRoute, navOptions)
}