package com.example.plantcare.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.BuildConfig
import com.example.plantcare.api.getRetrofit
import com.example.plantcare.data.remote.dto.PlantRequest
import com.example.plantcare.ui.states.PlantIdentifierUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantIdentifierViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantIdentifierUiState())
    val uiState: StateFlow<PlantIdentifierUiState> = _uiState
    private var lastValidState: PlantIdentifierUiState? = null
    fun identify(uri: Uri, context: Context) {
        val imageBase64 = imageToBase64(uri, context)
        val request = PlantRequest(images = listOf(imageBase64))
        val apiKey = BuildConfig.API_KEY
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val details = listOf(
                    "common_names",
                    "url",
                    "image",
                    "description_all",
                    "watering",
                    "best_watering",
                    "toxicity"
                ).joinToString(",")
                val response = getRetrofit().identifyPlant(
                    apiKey,
                    request,
                    details
                )
                if (response.isSuccessful) {
                    val suggestion = response.body()
                        ?.result
                        ?.classification
                        ?.suggestions
                        ?.firstOrNull()
                    val newState = PlantIdentifierUiState(
                        plantName = suggestion?.name ?: "Não encontrada",
                        plantImage = suggestion?.details?.image?.value ?: "",
                        commonNames = suggestion?.details?.common_names ?: emptyList(),
                        description = suggestion?.details?.description_all?.value ?: "",
                        toxicity = suggestion?.details?.toxicity ?: "",
                        plantUrl = suggestion?.details?.url ?: "",
                        probability = suggestion?.probability ?: 0.0,
                        bestWatering = suggestion?.details?.best_watering ?: "",
                        isLoading = false,
                        errorMessage = null
                    )
                    _uiState.update { newState }
                    lastValidState = newState
                } else {
                    val errorBody = response.errorBody()?.string()
                    _uiState.update {
                        it.copy(
                            plantName = "Erro ${response.code()}: $errorBody",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("PlantIdentifier", "Erro inesperado", e)
                println(e.localizedMessage)
                _uiState.update {
                    it.copy(
                        plantName = "Erro: ${e.localizedMessage}",
                        isLoading = false
                    )
                }

            }

        }
    }


    private fun imageToBase64(uri: Uri, context: Context): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return ""
        return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
    }
}
