package com.example.plantcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.models.Plant
import com.example.plantcare.models.PlantSuggestion
import com.example.plantcare.ui.states.PlantSuggestionListUiState
import com.example.plantcare.ui.theme.PlantCareTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    uiState: PlantSuggestionListUiState,
    onPlantSuggestionClick: (PlantSuggestion) -> Unit = {},
    onExitToAppClick: () -> Unit = {},
    onNewRequestClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavController,
    ) {
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
                            "History",
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
                onClick = onNewRequestClick,
                containerColor = Color(0xFF9DC384)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)

                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Nova Resquest",
                        tint = Color(0xFF6B4226)
                    )
                    Text("New Search", color = Color(0xFF6B4226))
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
        if (uiState.plantSuggestion.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF9DC384)),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma identificação realizada.")
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.plantSuggestion) { item ->
                    Row(Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            onClick = {
                                onPlantSuggestionClick(item)
                            }
                        )
                        .padding(16.dp)
                    ) {
                        HistoryItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(item: PlantSuggestion) {
    val dateFormatted = remember(item.identifiedAt) {
        val date = Date(item.identifiedAt)
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${item.name}",
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = dateFormatted,
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoricoScreen() {
    PlantCareTheme {
        HistoryScreen(
            uiState = PlantSuggestionListUiState(
                plantSuggestion = listOf(
                    PlantSuggestion(
                        accessToken = "abc123",
                        identifiedAt = System.currentTimeMillis(),
                        name = "Ficus lyrata",
                        probability = 0.95,
                        image = "https://example.com/ficus.jpg",
                        common_names = "Figueira-lira",
                        url = "https://en.wikipedia.org/wiki/Ficus_lyrata",
                        descriptionValue = "Ficus lyrata é uma espécie de planta com folhas grandes e decorativas.",
                        best_watering = "Regar a cada 7 dias",
                        toxicity = "Tóxica para animais domésticos",
                        imageValue = "https://example.com/ficus_large.jpg"
                    )
                )
            ),
            navController = NavController(LocalContext.current)
        )
    }
}
