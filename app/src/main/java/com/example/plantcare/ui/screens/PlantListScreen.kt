package com.example.plantcare.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.states.PlantListUiState
import com.example.plantcare.ui.theme.PlantCareTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlantListScreen(
    uiState: PlantListUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
    onNewPlantClick: () -> Unit = {},
    onPlantClick: (Plant) -> Unit = {},
    onExitToAppClick: () -> Unit = {},
) {
    var expandedPlantId by remember { mutableStateOf<String?>(null) }
    Scaffold(
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
                            "My Plants",
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
        containerColor = Color(0x339DC384),
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF9DC384)
            ) {
                listOf(
                    Icons.Default.Home to "Home" to "plantCareGraph",
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
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)

        ) {
            items(uiState.plants) { plant ->

                Row(Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(0.5.dp, Color(0xFFEFEFEF), RoundedCornerShape(12.dp))
                    .combinedClickable(
                        onClick = {
                            expandedPlantId = if (expandedPlantId == plant.id) null else plant.id
                        },
                        onLongClick = {
                            onPlantClick(plant)
                        }
                    )
                    .padding(16.dp)
                ) {
                    Box(
                        Modifier
                            .size(35.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                uiState.onPlantWateredChange(plant)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val today = LocalDate.now().toEpochDay()
                        val needsWatering = plant.nextWatering <= today
                        val iconColor =
                            if (!plant.isWatered && needsWatering) Color.Blue else Color.Gray
                        Icon(
                            painter = painterResource(id = R.drawable.water_drop),
                            contentDescription = "Ícone de rega",
                            tint = iconColor,
                            modifier = Modifier.size(27.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = plant.name,
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                        AnimatedVisibility(visible = expandedPlantId == plant.id)
                        {
                            Text(
                                text = "Próxima rega: ${
                                    LocalDate.ofEpochDay(plant.nextWatering).format(dateFormatter)
                                }",
                                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF9DC384))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.potted_plant),
            contentDescription = "App Icon",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "My Plants",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6B4226),
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /* Notificação */ }) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color(0xFF6B4226)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlantsListScreenPreview() {
    PlantCareTheme {
        PlantListScreen(
            uiState = PlantListUiState(
                plants = listOf(
                    Plant(
                        id = "1",
                        name = "Espada-de-São-Jorge",
                        isWatered = false,
                        nextWatering = LocalDate.now().toEpochDay(),
                        wateringFrequency = "2",
                        species = "especia de espada de sao jorge",
                        lastWatered = "2023-09-01",
                        imageRes = R.drawable.plant_icon,
                        timeToWater = "13:00"
                    )
                ),
                onPlantWateredChange = {}
            ),
            navController = NavController(LocalContext.current)
        )
    }
}
