package com.example.plantcare.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.plantcare.R
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.states.PlantListUiState
import com.example.plantcare.ui.theme.PlantCareTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantListScreen(
    uiState: PlantListUiState,
    modifier: Modifier = Modifier,
    onNewPlantClick: () -> Unit = {},
    onPlantClick: (Plant) -> Unit = {}
) {
    var expandedPlantId by remember { mutableStateOf<String?>(null) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        ExtendedFloatingActionButton(
            onClick = onNewPlantClick,
            Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)

            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar nova planta")
                Text("Nova planta")
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Header()
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 60.dp)

        ) {
            items(uiState.plants) { plant ->
                var showPlantDetails by remember {
                    mutableStateOf(false)
                }
                Row(Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            expandedPlantId = if (expandedPlantId == plant.id) null else plant.id
                        },
                        onLongClick = {
                            onPlantClick(plant)
                        }
                    )) {
                    Box(
                        Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            )
                            .size(30.dp)
                            .border(
                                border = BorderStroke(2.dp, color = Color.Gray),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                Log.i("PlantListScreen", "$plant")
                                uiState.onPlantWateredChange(plant)
                            }
                    ){
                        if (plant.isWatered) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Done icon",
                                Modifier
                                    .size(100.dp),
                                tint = Color.Green
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = plant.name,
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        plant.nextWatering.let { nextWatering ->
                            AnimatedVisibility(visible = expandedPlantId == plant.id && nextWatering.toString().isNotBlank()) {
                                Text(
                                    text =  LocalDate.ofEpochDay(nextWatering).format(dateFormatter),
                                    style = TextStyle.Default.copy(fontSize = 18.sp)
                                )
                            }
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
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.plant_icon), // Ícone da folha
            contentDescription = "App Icon",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "PlantCare",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /* Notificação */ }) {
            //Icon(Icons.Default.Add, contentDescription = "Adicionar nova planta")
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.Gray
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

            )
        )
    }
}
