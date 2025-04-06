package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantcare.ui.states.PlantIdentifierUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlantResultViewModel : ViewModel() {

    private val _plantResult = MutableStateFlow<PlantIdentifierUiState?>(null)
    val plantResult: StateFlow<PlantIdentifierUiState?> = _plantResult

    fun setResult(result: PlantIdentifierUiState) {
        _plantResult.value = result
    }
}