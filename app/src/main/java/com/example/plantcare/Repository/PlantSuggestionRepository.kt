package com.example.plantcare.Repository

import com.example.plantcare.api.PlantIdService
import com.example.plantcare.data.remote.dto.PlantRequest
import com.example.plantcare.data.remote.dto.PlantResponse
import com.example.plantcare.database.dao.PlantSuggestionDao
import com.example.plantcare.database.entities.PlantSuggestionEntity
import com.example.plantcare.models.PlantSuggestion

class PlantSuggestionRepository(
    private val service: PlantIdService,
    private val dao: PlantSuggestionDao,
    private val apiKey: String
) {

    suspend fun identifyAndStore(request: PlantRequest, details: String): Result<PlantResponse> {
        return try {
            val response = service.identifyPlant("0uhjV9XFtRyiHaQWtXfKJGoSWiB7vy2uM12a8yPtiI4qGdFFlE", request, details)
            val accessToken = response.body()?.access_token
                ?: return Result.failure(Exception("Access token não encontrado"))

            dao.insert(
                PlantSuggestionEntity(
                    accessToken = accessToken,
                    name = response.body()?.result?.classification?.suggestions?.firstOrNull()?.name,
                    probability = response.body()?.result?.classification?.suggestions?.firstOrNull()?.probability,
                    image = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.image?.value,
                    common_names = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.common_names?.firstOrNull(),
                    url = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.url,
                    descriptionValue = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.description_all?.value,
                    best_watering = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.best_watering,
                    toxicity = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.toxicity,
                    imageValue = response.body()?.result?.classification?.suggestions?.firstOrNull()?.details?.image?.value
                )
            )

            Result.success(response.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    val suggestions get() = dao.getAll()
}

fun PlantSuggestion.toPlantSuggestionEntity(): PlantSuggestionEntity? {
    return PlantSuggestionEntity(
            accessToken = this.accessToken,
            identifiedAt = this.identifiedAt,
            name = this.name,
            probability = this.probability,
            image = this.image,
            common_names = this.common_names,
            url = this.url,
            descriptionValue = this.descriptionValue,
            best_watering = this.best_watering,
            toxicity = this.toxicity,
            imageValue = this.imageValue
    )
}

fun PlantSuggestionEntity.toPlantSuggestion(): PlantSuggestion {
    return PlantSuggestion(
        accessToken = this.accessToken,
        identifiedAt = this.identifiedAt,
        name = this.name,
        probability = this.probability,
        image = this.image,
        common_names = this.common_names,
        url = this.url,
        descriptionValue = this.descriptionValue,
        best_watering = this.best_watering,
        toxicity = this.toxicity,
        imageValue = this.imageValue
    )
}
