package com.example.plantcare.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantcare.models.Plant
import java.util.UUID

@Entity
data class UserEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val password: String,
    val profilePicture: String?,
    val plants: List<Plant> = emptyList()
)
