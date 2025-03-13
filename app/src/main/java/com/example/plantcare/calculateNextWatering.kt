package com.example.plantcare

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun calculateNextWatering(wateringFrequency: Int): String {
    val currentDate = LocalDate.now()
    val nextWateringDate = currentDate.plusDays(wateringFrequency.toLong())
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return nextWateringDate.format(formatter)
}