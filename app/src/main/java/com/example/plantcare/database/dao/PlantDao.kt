package com.example.plantcare.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantcare.database.entities.PlantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM PlantEntity")
    fun getAll(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM PlantEntity WHERE id = :plantId")
    fun getById(plantId: String): Flow<PlantEntity?>

    @Query("SELECT * FROM PlantEntity WHERE nextWatering <= :currentDate")
    suspend fun getPlantsToWater(currentDate: Long): List<PlantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: PlantEntity)

    @Delete
    suspend fun delete(plant: PlantEntity)

    @Update
    suspend fun update(plant: PlantEntity)

}