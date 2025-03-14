package com.example.plantcare.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class PlantEntity (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val species: String? = null,
    val wateringFrequency: String,
    val lastWatered: String? = null,
    val nextWatering: String,
    val isWatered: Boolean = false,
    val imageRes: Int? = 0
)