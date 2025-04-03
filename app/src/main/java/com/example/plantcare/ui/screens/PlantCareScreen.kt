package com.example.plantcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
                    IconButton(onClick = onExitToAppClick) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sair do app")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    Icons.Default.Home to "Home" to "home",
                    Icons.Default.Notifications to "Lembretes" to "notifications",
                    Icons.Default.PlayArrow to "Time-lapse" to "timelapse",
                    Icons.Default.Settings to "Config" to "settings"
                ).forEach { (iconLabelPair, route) ->
                    val (icon, label) = iconLabelPair
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(route) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Acesso Rápido", style = MaterialTheme.typography.headlineSmall)

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(homeMenuItems.size) { index ->
                    val item = homeMenuItems[index]
                    HomeMenuButton(icon = item.first, text = item.second) {
                        navController.navigate(item.third)
                    }
                }
            }
        }
    }
}

val homeMenuItems = listOf(
    Triple(Icons.AutoMirrored.Filled.List, "Minhas Plantas", "plantList"),
    Triple(Icons.Default.DateRange, "Cronograma", "notifications"),
    Triple(Icons.Default.Notifications, "Notificações", "notifications"),
    Triple(Icons.Default.PlayArrow, "Time-lapse", "timelapse"),
    Triple(Icons.Default.Info, "Diário", "plant_diary"),
    Triple(Icons.Default.Settings, "Configurações", "settings")
)

@Composable
fun HomeMenuButton(icon: ImageVector, text: String, onClick: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun PreviewPlantCareScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PlantCareScreen(
            navController = NavController(LocalContext.current)
        )
    }
}
