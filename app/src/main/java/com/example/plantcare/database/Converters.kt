package com.example.plantcare.database

import androidx.compose.ui.input.key.type
import androidx.room.TypeConverter
import com.example.plantcare.models.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromPlantList(plants: List<Plant>): String {
        return Gson().toJson(plants)
    }

    @TypeConverter
    fun toPlantList(plantsString: String): List<Plant> {
        val type = object : TypeToken<List<Plant>>() {}.type
        return Gson().fromJson(plantsString, type)
    }
}