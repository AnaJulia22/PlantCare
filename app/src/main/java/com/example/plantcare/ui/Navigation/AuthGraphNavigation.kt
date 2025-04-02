package com.example.plantcare.ui.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation

const val authGraphRoute = "authGraph"

fun NavGraphBuilder.authGraph(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHomeGraph: (NavOptions) -> Unit,
    onNavigateToSignIn: (NavOptions) -> Unit
) {
    navigation(
        route = authGraphRoute,
        startDestination = signInRoute
    ) {
        signInScreen(
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToPlantList = {
                onNavigateToHomeGraph(navOptions {
                    popUpTo(authGraphRoute)
                })
            }
        )
        signUpScreen(
            onNavigationToSignIn = {
                onNavigateToSignIn(navOptions {
                    popUpTo(authGraphRoute)
                })
            }
        )
    }
}

fun NavHostController.navigateToAuthGraph(){
    navigate(authGraphRoute)
}