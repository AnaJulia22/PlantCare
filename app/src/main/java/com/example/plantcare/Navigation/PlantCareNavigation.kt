/*
package com.example.plantcare.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun PlantCareNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("plant_details/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")
            PlantDetailsScreen(navController, plantId)
        }
        composable("notifications") { NotificationsScreen(navController) }
        composable("timelapse") { TimelapseScreen(navController) }
        composable("plant_diary") { PlantDiaryScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
*/
