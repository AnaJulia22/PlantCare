package com.example.plantcare.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add the timeToWater column to the PlantEntity table
            database.execSQL("ALTER TABLE `PlantEntity` ADD COLUMN `timeToWater` TEXT")
        }
    }
}