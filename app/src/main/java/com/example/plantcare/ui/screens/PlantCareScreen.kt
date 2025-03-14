package com.example.plantcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.plantcare.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCareScreen(navController: NavController) {
    Scaffold(
        topBar = {
            /* Exibir perfil */// Ícone de perfil
            TopAppBar(title = { Text("Flora Friend") },
                actions = fun RowScope.() {
                    // Ícone de perfil
                    IconButton(onClick = { /* Exibir perfil */ }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil"
                        )
                    }
                })
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle add new plant */ },
                modifier = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Adicionar planta")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
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
                    onClick = { /* Navegar para lista de plantas */ }
                )
                HomeMenuButton(
                    icon = Icons.Default.WaterDrop,
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
                    icon = Icons.Default.Movie,
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
                    icon = Icons.Default.Book,
                    text = "Diário",
                    onClick = { navController.navigate("plant_diary") }
                )
                HomeMenuButton(
                    icon = Icons.Default.Settings,
                    text = "Configurações",
                    onClick = { navController.navigate("settings") }
                )
            }

            // Lista de plantas (exemplo simplificado)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Suas Plantas",
                style = MaterialTheme.typography.headlineSmall
            )
            // Aqui iria um LazyColumn com a lista de plantas
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        "Home" to "home",
        "Notifications" to "notifications",
        "Settings" to "settings"
    )

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        items.forEach { (title, route) ->
            NavigationBarItem(
                icon = {
                    when (title) {
                        "Home" -> Icon(Icons.Default.Add, contentDescription = "Home")
                        "Notifications" -> Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        "Settings" -> Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                label = { Text(title) },
                selected = false,
                onClick = { navController.navigate(route) }
            )
        }
    }
}