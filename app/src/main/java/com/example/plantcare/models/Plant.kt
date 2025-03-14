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

// Modelo de Usuário
data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
    val registrationDate: Long = System.currentTimeMillis(),
    val settings: UserSettings = UserSettings()
)

// Configurações do usuário
data class UserSettings(
    val darkMode: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val notificationTime: String = "09:00" // Formato HH:MM
)

data class TimelapseImage(
    val url: String,
    val date: Long // timestamp
)

data class WateringReminder(
    val id: String,
    val plantId: String,
    val plantName: String,
    val scheduledDate: Long, // timestamp
    val completed: Boolean = false,
    val skipped: Boolean = false
)