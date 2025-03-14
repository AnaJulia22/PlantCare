package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.Repository.toPlant
import com.example.plantcare.models.Plant
import com.example.plantcare.ui.states.PlantFormUiState
import com.example.plantcare.ui.states.PlantListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantHomeViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlantListUiState> =
        MutableStateFlow(PlantListUiState())

    val uiState
        get() = _uiState
        .combine(repository.plants) { uiState, plants ->
            uiState.copy(plants = plants.map { it.toPlant() })
        }

    init {
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onPlantWateredChange = { plant ->
                    viewModelScope.launch {
                        repository.toggleIsWatered(plant)
                    }
                })
            }
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            repository.save(plant)
            loadPlants()
        }
    }
}