package com.example.plantcare.models

import java.util.UUID

data class Plant(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val species: String? = null,
    val wateringFrequency: String,
    val lastWatered: String? = null,
    val nextWatering: String,
    val isWatered: Boolean = false,
    val imageRes: Int?
) {

}