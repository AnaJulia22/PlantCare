package com.example.plantcare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantcare.database.dao.PlantDao
import com.example.plantcare.database.entities.PlantEntity

@Database(entities = [PlantEntity::class], version = 2)
abstract class PlantDataBase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile private var instance: PlantDataBase? = null

        fun getDatabase(context: Context): PlantDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PlantDataBase::class.java,
                    "plant_database"
                ).build().also { instance = it }
            }
        }
    }
}