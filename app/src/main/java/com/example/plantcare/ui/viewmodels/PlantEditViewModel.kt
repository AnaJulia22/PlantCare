package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.ui.states.PlantFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlantEditViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlantFormUiState> =
        MutableStateFlow(PlantFormUiState())

    val uiState = _uiState.asStateFlow()
}