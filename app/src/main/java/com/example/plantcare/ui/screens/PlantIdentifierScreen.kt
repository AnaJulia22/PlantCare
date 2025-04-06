package com.example.plantcare.ui.screens

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantcare.ui.states.PlantIdentifierUiState

@Composable
fun PlantIdentifierScreen(
    uiState: PlantIdentifierUiState,
    onImageSelected: (Uri, context: android.content.Context) -> Unit,
    onOpenCamera: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it, context) }
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
        } else {
            if (uiState.plantImage.isNotBlank()) {
                AsyncImage(
                    model = uiState.plantImage,
                    contentDescription = "Imagem semelhante",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🌿 Planta identificada: ${uiState.plantName}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            val uriHandler = LocalUriHandler.current

            if (uiState.plantUrl.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { uriHandler.openUri(uiState.plantUrl) }) {
                    Text("🌐 Ver mais sobre a planta")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "📊 Probabilidade de ser uma planta: ${uiState.probability}%")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "📜 Descrição:")
            Text(text = uiState.description.ifBlank { "Sem descrição disponível." })

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.commonNames.isNotEmpty()) {
                Text(text = "📚 Nomes populares:")
                uiState.commonNames.forEach { name ->
                    Text(text = "• $name")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.bestWatering.isNotBlank()) {
                Text(text = "💦 Melhor momento para regar:")
                Text(text = uiState.bestWatering)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.toxicity.isNotBlank()) {
                Text(text = "☠️ Toxicidade:")
                Text(text = uiState.toxicity)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantIdentifierScreenPreview() {
    PlantIdentifierScreen(
        uiState = PlantIdentifierUiState(
            isLoading = false,
            plantName = "Roseira",
            plantImage = "",
            description = "A roseira é uma planta ornamental muito comum.",
            commonNames = listOf("Rosa", "Flor de Maio"),
            plantUrl = "https://example.com/roseira.jpg",
            bestWatering = "Pela manhã ou no final da tarde",
            probability = 90.0,
            toxicity = "Tóxica para animais domésticos"
        ),
        onImageSelected = { _, _ -> },
        onOpenCamera = { /* Preview: ação simulada */ }
    )
}
