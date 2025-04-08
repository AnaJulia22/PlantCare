package com.example.plantcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.plantcare.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCareScreen(
    navController: NavController,
    onExitToAppClick: () -> Unit = {},
    onNewPlantClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color(0x339DC384),
        topBar = {

            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.potted_plant),
                            contentDescription = "App Icon",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "PlantCare",
                            color = Color(0xFF6B4226),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9DC384)
                ),
                actions = {
                    IconButton(onClick = onExitToAppClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sair do app",
                            tint = Color(0xFF6B4226)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNewPlantClick,
                containerColor = Color(0xFF9DC384)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Adicionar nova planta",
                        tint = Color(0xFF6B4226)
                    )
                    Text("New Plant", color = Color(0xFF6B4226))
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF9DC384)
            ) {
                listOf(
                    Icons.Default.Home to "Home" to "home",
                    Icons.Default.Notifications to "Notifications" to "notifications",
                    Icons.AutoMirrored.Filled.List to "My Plants" to "plantList",
                    Icons.Default.CameraEnhance to "Identify" to "plant_identifier"
                ).forEach { (iconLabelPair, route) ->
                    val (icon, label) = iconLabelPair
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(route) },
                        icon = {
                            Icon(
                                icon,
                                contentDescription = label,
                                tint = Color(0xFF6B4226)
                            )
                        },
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
    Triple(Icons.AutoMirrored.Filled.List, "My Plants", "plantList"),
    Triple(Icons.Default.DateRange, "Calendar", "notifications"),
    Triple(Icons.Default.Notifications, "Notifications", "notifications"),
    //Triple(Icons.Default.PlayArrow, "Time-lapse", "timelapse"),
    Triple(Icons.Default.CameraEnhance, "Identify", "plant_identifier")
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
                tint = Color(0xFF6B4226),
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
