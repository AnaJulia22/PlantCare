package com.example.plantcare.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
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
        Text(
            text = "PlantCare AI 🌱",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF6B4226),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9DC384),
                contentColor = Color(0xFF6B4226)
            ),
            modifier = Modifier
                .fillMaxSize(0.8f)
                .clip(RoundedCornerShape(16.dp))
            ) {
            Text("Select Image from gallery", color = Color(0xFF6B4226))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onOpenCamera,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9DC384),
                contentColor = Color(0xFF6B4226)
            ),
            modifier = Modifier
                .fillMaxSize(0.8f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text("Use Camera", color = Color(0xFF6B4226))
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
    val mockUiState = PlantIdentifierUiState(
        isLoading = false,
        plantName = "",
        errorMessage = null
    )
    PlantIdentifierScreen(
        onImageSelected = { _, _ -> },
        onOpenCamera = { /* Preview: ação simulada */ },
        onNavigateToResult = { /* Preview: ação simulada */ }
    )
}
