package com.example.plantcare.ui.Navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.plantcare.ui.screens.SignInScreen
import com.example.plantcare.ui.viewmodels.SignInViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.platform.LocalContext
import com.example.plantcare.Authentication.GoogleAuthClient
import kotlinx.coroutines.launch

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    onNavigateToPlantCare: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val googleAuthClient = remember { GoogleAuthClient(context) }
        val isAuthenticated by viewModel.isAuthenticated
            .collectAsState(initial = false)
        LaunchedEffect(isAuthenticated) {
            if (isAuthenticated) {
                onNavigateToPlantCare()
            }
        }
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignInWithGoogleClick = {
                scope.launch {
                    val isSuccess = googleAuthClient.signIn()
                    if (isSuccess) {
                        onNavigateToPlantCare()
                    } else {
                        Log.e("SignIn", "Erro no login com Google")
                    }
                }
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