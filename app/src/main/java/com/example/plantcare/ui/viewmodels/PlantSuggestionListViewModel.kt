package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantcare.Authentication.FirebaseAuthRepository
import com.example.plantcare.Repository.PlantSuggestionRepository
import com.example.plantcare.Repository.toPlantSuggestion
import com.example.plantcare.ui.states.PlantSuggestionListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class PlantSuggestionListViewModel(
    private val repository: PlantSuggestionRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PlantSuggestionListUiState> =
        MutableStateFlow(PlantSuggestionListUiState())
    val uiState
        get() = _uiState
            .combine(repository.suggestions) { uiState, plants ->
                uiState.copy(plantSuggestion = plants.map { it.toPlantSuggestion() })
            }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}