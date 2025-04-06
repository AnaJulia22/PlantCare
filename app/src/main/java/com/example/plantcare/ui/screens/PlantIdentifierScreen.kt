package com.example.plantcare.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantIdentifierScreen(
    viewModel: PlantIdentifierViewModel = koinViewModel(),
    onImageSelected: (Uri, context: android.content.Context) -> Unit,
    onOpenCamera: () -> Unit,
    onNavigateToResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it, context) }

    }

    LaunchedEffect(uiState.plantName) {
        Log.d("NAVIGATION", "Estado atual: ${uiState?.plantName}, isLoading: ${uiState?.isLoading}")
        if (uiState?.plantName?.isNotBlank() == true && !uiState.isLoading) {
            Log.d("NAVIGATION", "Navegando para resultados")
            onNavigateToResult()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Selecionar Imagem da Planta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para capturar imagem com a câmera
        Button(onClick = onOpenCamera) {
            Text("Usar Câmera")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.errorMessage != null) {
            Text("Erro: ${uiState.errorMessage}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantIdentifierScreenPreview() {
    PlantIdentifierScreen(
        /*uiState = PlantIdentifierUiState(
            isLoading = false,
            plantName = "Roseira",
            plantImage = "",
            description = "A roseira é uma planta ornamental muito comum.",
            commonNames = listOf("Rosa", "Flor de Maio"),
            plantUrl = "https://example.com/roseira.jpg",
            bestWatering = "Pela manhã ou no final da tarde",
            probability = 90.0,
            toxicity = "Tóxica para animais domésticos"
        ),*/
        onImageSelected = { _, _ -> },
        onOpenCamera = { /* Preview: ação simulada */ },
        onNavigateToResult = { /* Preview: ação simulada */ }
    )
}
