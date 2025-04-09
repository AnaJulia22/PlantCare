package com.example.plantcare.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantcare.database.dao.PlantDao
import com.example.plantcare.database.entities.PlantEntity

@Database(entities = [PlantEntity::class], version = 2)
abstract class PlantDataBase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}