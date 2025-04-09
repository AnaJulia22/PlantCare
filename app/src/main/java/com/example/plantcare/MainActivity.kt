package com.example.plantcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.database.PlantDataBase
import com.example.plantcare.ui.Navigation.authGraph
import com.example.plantcare.ui.Navigation.authGraphRoute
import com.example.plantcare.ui.Navigation.homeGraph
import com.example.plantcare.ui.Navigation.homeGraphRoute
import com.example.plantcare.ui.Navigation.navigateToCamera
import com.example.plantcare.ui.Navigation.navigateToEditPlantForm
import com.example.plantcare.ui.Navigation.navigateToHomeGraph
import com.example.plantcare.ui.Navigation.navigateToNewPlantForm
import com.example.plantcare.ui.Navigation.navigateToPlantIdentifier
import com.example.plantcare.ui.Navigation.navigateToPlantResult
import com.example.plantcare.ui.Navigation.navigateToPlantResultDetails
import com.example.plantcare.ui.Navigation.navigateToSignIn
import com.example.plantcare.ui.Navigation.navigateToSignUp
import com.example.plantcare.ui.theme.PlantCareTheme
import com.google.firebase.FirebaseApp
import org.koin.core.annotation.KoinExperimentalAPI


class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        val googleAuthClient = GoogleAuthClient(this)
        setContent {
            PlantCareTheme {
                val navController = rememberNavController()
                var startDestination by rememberSaveable { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    startDestination = if (googleAuthClient.isSingedIn()) {
                        homeGraphRoute
                    } else {
                        authGraphRoute
                    }
                }
                startDestination?.let {
                    NavHost(
                        navController = navController,
                        startDestination = it
                    ) {
                        authGraph(
                            onNavigateToHomeGraph = {
                                navController.navigateToHomeGraph(it)
                            }, onNavigateToSignIn = {
                                navController.navigateToSignIn(it)
                            },
                            onNavigateToSignUp = {
                                navController.navigateToSignUp()
                            }
                        )
                        homeGraph(
                            onNavigateToNewPlantForm = {
                                navController.navigateToNewPlantForm()
                            },
                            onNavigateToEditPlantForm = { plant ->
                                navController.navigateToEditPlantForm(plant)
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            navController = navController,
                            onNavigateToLogin = {
                                navController.navigateToSignIn()
                            },
                            onNavigateToCamera = {
                                navController.navigateToCamera()
                            },
                            onNavigateToResult = {
                                navController.navigateToPlantResult()
                            },
                            onNavigateToPlantDetails = {
                                navController.navigateToPlantResultDetails()
                            }
                        )
                    }
                }
            }
        }
    }
}
