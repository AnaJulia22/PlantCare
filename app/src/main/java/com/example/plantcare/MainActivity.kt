package com.example.plantcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.database.PlantDataBase
import com.example.plantcare.ui.Navigation.authGraph
import com.example.plantcare.ui.Navigation.authGraphRoute
import com.example.plantcare.ui.Navigation.homeGraph
import com.example.plantcare.ui.Navigation.navigateToCamera
import com.example.plantcare.ui.Navigation.navigateToEditPlantForm
import com.example.plantcare.ui.Navigation.navigateToHomeGraph
import com.example.plantcare.ui.Navigation.navigateToNewPlantForm
import com.example.plantcare.ui.Navigation.navigateToPlantIdentifier
import com.example.plantcare.ui.Navigation.navigateToSignIn
import com.example.plantcare.ui.Navigation.navigateToSignUp
import com.example.plantcare.ui.theme.PlantCareTheme
import com.google.firebase.FirebaseApp
import org.koin.core.annotation.KoinExperimentalAPI


class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = PlantDataBase.getDatabase(this)
        val plantDao = db.plantDao()

        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        val googleAuthClient = GoogleAuthClient(this)

        //val plantRepository = PlantRepository()
        //val viewModel = PlantViewModel(plantRepository)
        
        setContent {
            PlantCareTheme {
                val navController = rememberNavController()
                /*var isSignIn by rememberSavable {
                    mutableStateOf(googleAuthClient.isSingedIn())
                }

                LaunchedEffect(Unit) {
                    if (isSignIn) {
                        navController.navigate("plantCare")
                    }
                }
*/
                NavHost(
                    navController = navController,
                    startDestination = authGraphRoute
                ){
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
                            navController.navigateToPlantIdentifier()
                        }
                    )
                }
            }
        }
    }
}
