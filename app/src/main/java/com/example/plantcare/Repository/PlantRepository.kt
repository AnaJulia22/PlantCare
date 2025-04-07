package com.example.plantcare.Repository

import com.example.plantcare.models.Plant
import com.example.plantcare.database.dao.PlantDao
import com.example.plantcare.database.entities.PlantEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PlantRepository (
    private val dao: PlantDao
) {
    val plants get() = dao.getAll()

    suspend fun save(plant: Plant) = withContext(IO) {
        dao.insert(plant.toPlantEntity())
    }

    suspend fun toggleIsWatered(plant: Plant) = withContext(IO) {
        val today = java.time.LocalDate.now()
        val frequency = plant.wateringFrequency.toLongOrNull() ?: 0L
        val newNextWatering = today.plusDays(frequency).toEpochDay()
        val entity = plant.copy(
            isWatered = !plant.isWatered,
            nextWatering = newNextWatering,
            lastWatered = today.toString(),
        ).toPlantEntity()

        dao.insert(entity)
    }

    suspend fun delete(id: String) {
        dao.delete(
            PlantEntity(
                id = id,
                name = "",
                wateringFrequency = "",
                nextWatering = "".toLongOrNull() ?: 0L,
                isWatered = false
            )
        )
    }

    fun getById(id: String) = dao.getById(id)

}


fun Plant.toPlantEntity() = PlantEntity(
    id = this.id,
    name = this.name,
    species = this.species,
    wateringFrequency = this.wateringFrequency,
    lastWatered = this.lastWatered,
    nextWatering = this.nextWatering,
    isWatered = this.isWatered,
    imageRes = this.imageRes,
    timeToWater = this.timeToWater
)

fun PlantEntity.toPlant() = Plant(
    id = this.id,
    name = this.name,
    species = this.species,
    wateringFrequency = this.wateringFrequency,
    lastWatered = this.lastWatered,
    nextWatering = this.nextWatering,
    isWatered = this.isWatered,
    imageRes = this.imageRes,
    timeToWater = this.timeToWater
)
