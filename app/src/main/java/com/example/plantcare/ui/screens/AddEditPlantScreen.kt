package com.example.plantcare.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantcare.models.Plant

@Composable
fun AddEditPlantScreen (
    plant: Plant? = null,
    onSave: (Plant) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(plant?.name ?: "") }
    var species by remember { mutableStateOf(plant?.species ?: "") }
    var wateringFrequency by remember { mutableStateOf(plant?.wateringFrequency ?: "") }
    var nextWatering by remember { mutableStateOf(plant?.nextWatering ?: "") }
    var isWatered by remember { mutableStateOf(plant?.isWatered ?: false) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome da Planta") }
        )
        TextField(
            value = species,
            onValueChange = { species = it },
            label = { Text("Espécie") }
        )
        TextField(
            value = wateringFrequency,
            onValueChange = { wateringFrequency = it },
            label = { Text("Frequência de Rega") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                onSave(Plant(
                    id = plant?.id ?: "",
                    name = name,
                    species = species,
                    wateringFrequency = wateringFrequency,
                    nextWatering = nextWatering,
                    isWatered = isWatered,
                    lastWatered = TODO(),
                    imageRes = TODO()
                ))
            }) {
                Text("Salvar")
            }
        }
    }
}