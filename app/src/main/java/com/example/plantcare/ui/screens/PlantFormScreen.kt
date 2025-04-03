package com.example.plantcare.ui.screens

import android.app.TimePickerDialog
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
import com.example.plantcare.Application.WorkScheduler
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.database.PlantDataBase
import com.example.plantcare.ui.states.PlantFormUiState
import com.example.plantcare.ui.theme.PlantCareTheme
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
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
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var selectedDate by remember {
        mutableStateOf(
            uiState.lastWatered?.takeIf { it.isNotEmpty() }?.let {
                try {
                    LocalDate.parse(it)
                } catch (e: Exception) {
                    LocalDate.now()
                }
            } ?: LocalDate.now()
        )
    }

    var selectedTime by remember {
        mutableStateOf(
            uiState.timeToWater?.takeIf { it.isNotEmpty() }?.let {
                try {
                    LocalTime.parse(it)
                } catch (e: Exception) {
                    LocalTime.now()
                }
            } ?: LocalTime.now()
        )
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var nextWatering by remember { mutableStateOf(0L) }

    LaunchedEffect(uiState.wateringFrequency, selectedDate) {
        if (uiState.wateringFrequency.isNotEmpty()) {
            val frequency = uiState.wateringFrequency.toIntOrNull() ?: 0
            println("Selected Date: $selectedDate")
            nextWatering = if (frequency > 0) {
                selectedDate.plusDays(frequency.toLong()).toEpochDay()
            } else {
                0L
            }
            uiState.onNextWateringChange(nextWatering)
        }
    }

    /*if (nextWatering == LocalDate.now().format(dateFormatter) && !uiState.isWatered) {
        sendWateringNotification(context, uiState.name)
    }*/


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
            val lastWateredDate = uiState.lastWatered
            Text(
                text = "Last Watering Date: $lastWateredDate",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { showDatePicker = true; uiState.onLastWateredChange(selectedDate.toString()) }
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Next Watering Date: ${uiState.nextWatering}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.size(8.dp))
            val time = uiState.timeToWater
            Text(
                text = "Watering Time: $time",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { showTimePicker = true; uiState.onTimeResChange(selectedTime.toString()) }
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        uiState.onLastWateredChange(selectedDate.toString())}) { Text("OK") }
                }
            ) {
                val datePickerState = rememberDatePickerState()
                DatePicker(state = datePickerState)
                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                        println("New Selected Date: $selectedDate")
                    }
                }
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    selectedTime = LocalTime.of(hour, minute)
                    uiState.onTimeResChange(selectedTime.toString())},
                selectedTime.hour,
                selectedTime.minute,
                true
            ).show()
        }


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            SaveButton(onSaveClick)
            WorkScheduler.scheduleWateringReminder(context, selectedTime, selectedDate, uiState.name)
            println(uiState.name)
            println(LocalDate.ofEpochDay(uiState.nextWatering).format(dateFormatter))
            println(uiState.timeToWater)
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
    LaunchedEffect(value, placeholder) {
        println("InputField - Current value: '$value', Placeholder: '$placeholder'")
    }
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