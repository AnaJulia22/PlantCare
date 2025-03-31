package com.example.plantcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.states.PlantListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCareScreen(
    navController: NavController,
    onExitToAppClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flora Friend") },
                actions = {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "ícone sair do app",
                        Modifier
                            .clip(CircleShape)
                            .clickable { onExitToAppClick() }
                            .padding(8.dp),
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Já estamos na tela inicial */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("notifications") },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Notificações") },
                    label = { Text("Lembretes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("timelapse") },
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Time-lapse") },
                    label = { Text("Time-lapse") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Configurações") },
                    label = { Text("Config") }
                )
            }
        },
        /*floatingActionButton = {
            FloatingActionButton(
                onClick = { *//* Handle add new plant *//* },
                modifier = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Adicionar planta")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }*/
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botões de acesso rápido
            Text(
                "Acesso Rápido",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeMenuButton(
                    icon = Icons.AutoMirrored.Filled.List,
                    text = "Minhas Plantas",
                    onClick = {
                        navController.navigate("plantList")
                    }
                )
                HomeMenuButton(
                    icon = Icons.Default.DateRange,
                    text = "Cronograma",
                    onClick = { navController.navigate("notifications") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeMenuButton(
                    icon = Icons.Default.Notifications,
                    text = "Notificações",
                    onClick = { navController.navigate("notifications") }
                )
                HomeMenuButton(
                    icon = Icons.Default.PlayArrow,
                    text = "Time-lapse",
                    onClick = { navController.navigate("timelapse") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeMenuButton(
                    icon = Icons.Default.Info,
                    text = "Diário",
                    onClick = { navController.navigate("plant_diary") }
                )
                HomeMenuButton(
                    icon = Icons.Default.Settings,
                    text = "Configurações",
                    onClick = { navController.navigate("settings") }
                )
            }

            /*// Lista de plantas (exemplo simplificado)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Suas Plantas",
                style = MaterialTheme.typography.headlineSmall
            )
            val viewModel = koinViewModel<PlantListViewModel>()
            val uiState by viewModel.uiState
                .collectAsState(PlantListUiState())
            PlantList(
                uiState = uiState
            )*/
        }
    }
}

@Composable
fun PlantList(uiState: PlantListUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(uiState.plants) { plant ->
            PlantItem(plant)
        }
    }
}

@Composable
fun PlantItem(plant: Plant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = plant.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Próxima rega: ${plant.nextWatering}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun HomeMenuButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.size(160.dp, 100.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text)
        }
    }
}

@Composable
fun UserProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.plant_icon),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Username",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantCareScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PlantCareScreen(
            navController = NavController(LocalContext.current)
        )
    }
}

@Composable
fun QuickAccessButtons(navController: NavController) {
    val quickAccessItems = listOf(
        "Minhas Plantas" to "plantList",
        "Cronograma de Rega" to "wateringSchedule",
        "Notificações" to "notifications",
        "Galeria de Time-Lapse" to "timeLapseGallery",
        "Diário da Planta" to "plantDiary",
        "Configurações" to "settings"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(quickAccessItems) { (title, route) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { navController.navigate(route) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
