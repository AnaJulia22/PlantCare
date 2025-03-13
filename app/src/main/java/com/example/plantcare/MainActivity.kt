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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantcare.ui.screens.PlantCareScreen
import com.example.plantcare.ui.screens.PlantFormScreen
import com.example.plantcare.ui.screens.PlantListScreen
import com.example.plantcare.ui.states.PlantListUiState
import com.example.plantcare.ui.viewmodels.PlantListViewModel
import com.example.plantcare.ui.theme.PlantCareTheme
import com.example.plantcare.ui.viewmodels.PlantFormViewModel
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
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
                var isSignIn by rememberSaveable {
                    mutableStateOf(googleAuthClient.isSingedIn())
                }

                LaunchedEffect(Unit) {
                    if (isSignIn) {
                        navController.navigate("plantCare")
                    }
                }
                KoinAndroidContext {
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                navController = navController,
                                isSignIn = isSignIn,
                                onSignIn = {
                                    lifecycleScope.launch {
                                        isSignIn = googleAuthClient.signIn()
                                        if (isSignIn) {
                                            navController.navigate("plantCare")
                                        }
                                    }
                                },
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthClient.signOut()
                                        isSignIn = false
                                    }
                                }
                            )
                        }
                        composable("plantCare") {
                            PlantCareScreen(navController)
                        }

                        // Tela de Listagem de Plantas
                        composable("plantList") {
                            val viewModel = koinViewModel<PlantListViewModel>()
                            val uiState by viewModel.uiState
                                .collectAsState(PlantListUiState())
                            PlantListScreen(
                                uiState = uiState,
                                onNewPlantClick = {
                                    navController.navigate("plantForm")
                                },
                                onPlantClick = { plant ->
                                    navController.navigate("plantForm?plantId=${plant.id}")
                                }
                            )
                            /*PlantListScreen(
                                viewModel = viewModel,
                                onAddPlant = { navController.navigate("addEditPlant") },
                                onEditPlant = { plant ->
                                    navController.navigate("addEditPlant/${plant.id}")
                                }
                            )*/
                        }

                        // Tela de Adicionar/Editar Planta
                        composable("plantForm?plantId={plantId}") {
                            val plantId = navArgument("plantId") {
                                nullable = true
                            }
                            val scope = rememberCoroutineScope()
                            val viewModel = koinViewModel<PlantFormViewModel>(
                                parameters = { parametersOf(plantId) })
                            val uiState by viewModel.uiState.collectAsState()
                            PlantFormScreen(
                                uiState = uiState,
                                onSaveClick = {
                                    scope.launch {
                                        viewModel.save()
                                        navController.popBackStack()
                                    }
                                },
                                onDeleteClick = {
                                    scope.launch {
                                        viewModel.delete()
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }

                        // Tela de Edição de Planta (com ID)
                        /*composable("addEditPlant/{plantId}") { backStackEntry ->
                            val plantId = backStackEntry.arguments?.getString("plantId")
                            val plant = viewModel.plants.value.find { it.id == plantId }
                            AddEditPlantScreen(
                                plant = plant,
                                onSave = { updatedPlant ->
                                    if (plant != null) {
                                        viewModel.updatePlant(plant.id, updatedPlant)
                                    }
                                    navController.popBackStack()
                                },
                                onCancel = { navController.popBackStack() }
                            )
                        }*/
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    navController: NavController,
    isSignIn: Boolean,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCCFFCC)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.plant_icon),
                contentDescription = "Plant Icon",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "PlantCare",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The best app for your plants",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            val navController = rememberNavController()
            if (isSignIn) {
                /*OutlinedButton(onClick = onSignOut) {
                    Text(
                        text = "Sign Out",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                }*/
                navController.navigate("plantCare")
            } else {
                OutlinedButton(onClick = onSignIn) {
                    Text(
                        text = "Sign In With Google",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}