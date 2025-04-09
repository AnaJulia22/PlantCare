package com.example.plantcare.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantcare.database.entities.PlantSuggestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantSuggestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(identification: PlantSuggestionEntity)

    @Query("SELECT * FROM plant_suggestions ORDER BY identifiedAt DESC")
    fun getAll(): Flow<List<PlantSuggestionEntity>>

    @Query("SELECT * FROM plant_suggestions WHERE accessToken = :token")
    suspend fun getByToken(token: String): PlantSuggestionEntity?
}