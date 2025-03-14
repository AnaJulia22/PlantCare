package com.example.plantcare.ui.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantcare.ui.states.PlantFormUiState
import com.example.plantcare.ui.theme.PlantCareTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantFormScreen(
    uiState: PlantFormUiState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    var nextWatering by remember { mutableStateOf("") }

    LaunchedEffect(uiState.wateringFrequency) {
        if (uiState.wateringFrequency.isNotEmpty()) {
            val frequency = uiState.wateringFrequency.toIntOrNull() ?: 0
            nextWatering = if (frequency > 0) {
                LocalDate.now().plusDays(frequency.toLong()).format(dateFormatter)
            } else {
                ""
            }
            uiState.onNextWateringChange(nextWatering)
        }
    }

    // Verifica se é o dia de regar e ainda não foi regado
    if (nextWatering == LocalDate.now().format(dateFormatter) && !uiState.isWatered) {
        sendWateringNotification(context, uiState.name)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val topAppBarName= uiState.topAppBarName
            val deleteEnabled = uiState.isDeleteEnabled
            TopAppBar(
                title = {
                    Text(
                        text = topAppBarName,
                        fontSize = 20.sp,
                    )
                },
                actions = {
                    if (deleteEnabled) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete plant icon",
                            Modifier
                                .clip(CircleShape)
                                .clickable {
                                    onDeleteClick()
                                }
                                .padding(4.dp)
                        )
                    }
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "Save plant icon",
                        Modifier
                            .clip(CircleShape)
                            .clickable {
                                onSaveClick()
                            }
                            .padding(4.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF03A9F4))
            )
            Spacer(modifier = Modifier.size(8.dp))
            InputField(
                value = uiState.name,
                onValueChange = uiState.onNameChange,
                placeholder = "Name",
                textStyle = TextStyle.Default.copy(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            InputField(
                value = uiState.species ?: "",
                onValueChange = uiState.onSpeciesChange,
                placeholder = "Species",
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            InputField(
                value = uiState.wateringFrequency,
                onValueChange = uiState.onWateringFrequencyChange,
                placeholder = "Watering frequency (days)",
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Next Watering: $nextWatering",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            SaveButton(onSaveClick)
        }
    }
}

@Composable
fun SaveButton(onSaveClick: () -> Unit) {
    Row(
        Modifier
            .padding(8.dp)
            .clickable { onSaveClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Done,
            contentDescription = "Save plant icon"
        )
        Spacer(Modifier.size(4.dp))
        Text(text = "Save")
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = textStyle.copy(
                        color = Color.Gray.copy(alpha = 0.5f)
                    ),
                )
            }
            innerTextField()
        },
        textStyle = textStyle
    )
}

fun sendWateringNotification(context: Context, plantName: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "plant_watering_reminder"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Watering Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = androidx.core.app.NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_popup_reminder)
        .setContentTitle("Time to water your plant!")
        .setContentText("Don't forget to water $plantName today.")
        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
        .build()

    notificationManager.notify(1, notification)
}

@Preview(showBackground = true)
@Composable
fun PlantFormScreenPreview() {
    PlantCareTheme {
        PlantFormScreen(
            uiState = PlantFormUiState(
                topAppBarName = "Criando planta"
            ),
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantFormScreenWithEditModePreview() {
    PlantCareTheme{
        PlantFormScreen(
            uiState = PlantFormUiState(
                topAppBarName = "Editando plant",
                isDeleteEnabled = true
            ),
            onSaveClick = {},
            onDeleteClick = {},
        )
    }
}