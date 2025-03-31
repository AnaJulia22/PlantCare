package com.example.plantcare.ui.Navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.SignInScreen
import com.example.plantcare.ui.viewmodels.SignInViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    onNavigateToPlantList: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(uiState.isAuthenticated) {
            if (uiState.isAuthenticated) {
                onNavigateToPlantList()
            }
        }
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                viewModel.authenticate()
            },
            onSignUpClick = onNavigateToSignUp
        )
    }

}

fun NavHostController.navigateToSignIn(
navOptions: NavOptions? = null
) {
navigate(signInRoute, navOptions)
}