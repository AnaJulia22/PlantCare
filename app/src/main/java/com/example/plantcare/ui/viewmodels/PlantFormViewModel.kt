package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.Repository.toPlant
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.states.PlantFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class PlantFormViewModel(
    private val repository: PlantRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState: MutableStateFlow<PlantFormUiState> =
        MutableStateFlow(PlantFormUiState())
    val uiState = _uiState.asStateFlow()

    private val id: String? = savedStateHandle["plantId"]

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onNameChange = { name ->
                    _uiState.update {
                        it.copy(name = name)
                    }
                },
                onSpeciesChange = { species ->
                    _uiState.update {
                        it.copy(species = species)
                    }
                },
                onWateringFrequencyChange = { wateringFrequency ->
                    _uiState.update {
                        it.copy(wateringFrequency = wateringFrequency)
                    }
                },
                onIsWateredChange = { isWatered ->
                    _uiState.update {
                        it.copy(isWatered = isWatered)
                    }
                },
                onLastWateredChange = { lastWatered ->
                    _uiState.update {
                        it.copy(lastWatered = lastWatered)
                    }
                },
                onNextWateringChange = { nextWatering ->
                    _uiState.update {
                        it.copy(nextWatering = nextWatering)
                    }
                },
                onImageResChange = { imageRes ->
                    _uiState.update {
                        it.copy(imageRes = imageRes)
                    }
                },
                onTimeResChange = { time ->
                    _uiState.update {
                        it.copy(timeToWater = time)
                    }
                },
                topAppBarName = "Creating New Plant"
            )
        }

        id?.let {
            viewModelScope.launch {
                repository.getById(id)
                    .filterNotNull()
                    .mapNotNull {
                        it.toPlant()
                    }.collectLatest { plant ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarName = "Editing Plant",
                                name = plant.name,
                                species = plant.species ?: "",
                                wateringFrequency = plant.wateringFrequency,
                                isWatered = plant.isWatered,
                                lastWatered = plant.lastWatered ?: "",
                                nextWatering = plant.nextWatering ?: 0L,
                                imageRes = plant.imageRes ?: 0,
                                timeToWater = plant.timeToWater ?: "",
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    suspend fun save() {
        with(_uiState.value) {
            val plantToSave = Plant(
                id = id ?: UUID.randomUUID().toString(),
                name = name,
                species = species,
                wateringFrequency = wateringFrequency,
                isWatered = isWatered,
                lastWatered = lastWatered,
                nextWatering = nextWatering,
                imageRes = imageRes,
                timeToWater = timeToWater
            )

            repository.save(plantToSave)

        }
    }

    suspend fun delete() {
        id?.let {
            repository.delete(id)
        }
    }

}