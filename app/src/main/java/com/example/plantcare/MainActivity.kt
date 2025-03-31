package com.example.plantcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantcare.di.appModule
import com.example.plantcare.ui.Navigation.authGraph
import com.example.plantcare.ui.Navigation.authGraphRoute
import com.example.plantcare.ui.Navigation.homeGraph
import com.example.plantcare.ui.Navigation.homeGraphRoute
import com.example.plantcare.ui.Navigation.navigateToEditPlantForm
import com.example.plantcare.ui.Navigation.navigateToHomeGraph
import com.example.plantcare.ui.Navigation.navigateToNewPlantForm
import com.example.plantcare.ui.Navigation.navigateToSignIn
import com.example.plantcare.ui.Navigation.navigateToSignUp
import com.example.plantcare.ui.screens.PlantCareScreen
import com.example.plantcare.ui.screens.PlantFormScreen
import com.example.plantcare.ui.screens.PlantListScreen
import com.example.plantcare.ui.states.PlantListUiState
import com.example.plantcare.ui.viewmodels.PlantListViewModel
import com.example.plantcare.ui.theme.PlantCareTheme
import com.example.plantcare.ui.viewmodels.PlantFormViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        }
                    )
                }
            }
        }
    }
}
