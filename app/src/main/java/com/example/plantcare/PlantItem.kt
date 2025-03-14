package com.example.plantcare

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantcare.models.Plant

@Composable
fun PlantItem(
    plant: Plant,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() }, // Permite editar ao clicar no item
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*Image(
                painter = painterResource(id = plant.imageRes), // Supondo que plant.imageRes seja um recurso drawable
                contentDescription = plant.name,
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))*/
            Column {
                Text(plant.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Next watering: ${plant.nextWatering}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            /*Text(
                text = plant.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )*/
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Regar a cada: ${plant.wateringFrequency}",
                fontSize = 14.sp
            )/*
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Excluir")
            }*/
        }
        /*Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = plant.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Espécie: ${plant.species}",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Regar a cada: ${plant.wateringFrequency}",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Excluir")
            }
        }*/
    }
}