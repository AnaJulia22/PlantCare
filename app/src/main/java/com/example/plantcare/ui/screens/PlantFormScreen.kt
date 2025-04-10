package com.example.plantcare.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantcare.Application.WorkScheduler
import com.example.plantcare.ui.states.PlantFormUiState
import com.example.plantcare.ui.theme.PlantCareTheme
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
    onCancelClick: () -> Unit,
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
                    null
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
                    null
                }
            } ?: LocalTime.now()
        )
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var nextWatering by remember { mutableStateOf(0L) }

    val date: LocalDate = LocalDate.now()

    LaunchedEffect(uiState.wateringFrequency, selectedDate) {
        if (uiState.wateringFrequency.isNotEmpty()) {
            val frequency = uiState.wateringFrequency.toIntOrNull() ?: 0
            println("Selected Date: $selectedDate")
            date.plusDays(frequency.toLong())
            nextWatering = if (frequency > 0) {
                selectedDate.plusDays(frequency.toLong()).toEpochDay()

            } else {
                0L
            }
            uiState.onNextWateringChange(nextWatering)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Color(0x339DC384)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val topAppBarName = uiState.topAppBarName
            val deleteEnabled = uiState.isDeleteEnabled
            TopAppBar(
                title = {
                    Text(
                        text = topAppBarName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    if (deleteEnabled) {
                        IconButton(onClick = onDeleteClick) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF9DC384))
            )
            Spacer(modifier = Modifier.size(8.dp))
            StyledInputField(
                value = uiState.name,
                onValueChange = uiState.onNameChange,
                label = "Name"
            )
            StyledInputField(
                value = uiState.species ?: "",
                onValueChange = uiState.onSpeciesChange,
                label = "Species"
            )
            StyledInputField(
                value = uiState.wateringFrequency,
                onValueChange = uiState.onWateringFrequencyChange,
                label = "Watering frequency (days)"
            )
            Spacer(modifier = Modifier.size(8.dp))

            val time = uiState.timeToWater
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x809DC384))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val lastWateredDate = uiState.lastWatered
                    println("Last Watered Date: $lastWateredDate")
                    Text(
                        text = "Last Watering Date: ${
                            uiState.lastWatered?.takeIf { it.isNotBlank() }?.let {
                                try {
                                    LocalDate.parse(it).format(dateFormatter)
                                } catch (e: Exception) {
                                    "Invalid date"
                                }
                            } ?: "--/--/----"
                        }",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable {
                                showDatePicker = true
                                uiState.onLastWateredChange(selectedDate.toString())
                            }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    val nextWateringFormatted =
                        LocalDate.ofEpochDay(uiState.nextWatering).format(dateFormatter)
                    Text(
                        text = "Next Watering Date:  ${
                            if (uiState.nextWatering > 0) {
                                LocalDate.ofEpochDay(uiState.nextWatering).format(dateFormatter)
                            } else {
                                "--/--/----"
                            }
                        }",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Watering Time: ${
                            uiState.timeToWater?.let { time ->
                                println("[DEBUG] Displaying time from state: $time")
                                try {
                                    LocalTime.parse(time).format(timeFormatter)
                                } catch (e: Exception) {
                                    println("[DEBUG] Error formatting time: ${e.message}")
                                    "--:--"
                                }
                            } ?: "--:--".also {
                                println("[DEBUG] No time set in state")
                            }
                        }",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable {
                                showTimePicker = true
                                uiState.onTimeResChange(selectedTime.toString())
                            }
                    )
                }
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        uiState.onLastWateredChange(selectedDate.toString())
                    }) { Text("OK") }
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
            LaunchedEffect(Unit) {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        selectedTime = LocalTime.of(hour, minute)
                        uiState.onTimeResChange(selectedTime.toString())
                    },
                    selectedTime.hour,
                    selectedTime.minute,
                    true
                ).show()
                showTimePicker = false
            }
        }
        val onSave: () -> Unit = {
            print(uiState.nextWatering)
            WorkScheduler.scheduleWateringReminder(
                context,
                selectedTime,
                date,
                uiState.name
            )
            onSaveClick()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onCancelClick() },
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(text = "Cancel")
            }

            SaveButton(onSaveClick = onSave)

        }

    }
}

@Composable
fun SaveButton(onSaveClick: () -> Unit) {
    Button(
        onClick = { onSaveClick() },
        modifier = Modifier
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9DC384))
    ) {
        Text(
            text = "Save",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun StyledInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
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
                topAppBarName = "Creating New Plant"
            ),
            onSaveClick = {},
            onCancelClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantFormScreenWithEditModePreview() {
    PlantCareTheme {
        PlantFormScreen(
            uiState = PlantFormUiState(
                topAppBarName = "Editing Plant",
                isDeleteEnabled = true
            ),
            onSaveClick = {},
            onCancelClick = {},
            onDeleteClick = {},
        )
    }
}