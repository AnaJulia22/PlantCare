package com.example.plantcare.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import com.example.plantcare.ui.viewmodels.PlantResultViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantIdentificationResultScreen(
    viewModel: PlantIdentifierViewModel = koinViewModel(),
    onDetailsClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    Log.d("DEBUG_RESULT", "Chegou aqui1")

    LaunchedEffect(Unit) {
        Log.d("DEBUG_RESULT", "Estado atual: ${uiState?.plantName}")
    }

    if (uiState.plantName.isBlank()) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Nenhuma planta identificada")
        }
    }

    uiState?.let { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "species identification",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF007F4E), // Verde suave
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.plantImage.take(2).forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "${"%.1f".format(state.probability * 100)}%".uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF007F4E)
                    )
                    Text(
                        text = "confirm",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = state.plantName,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )

            Text(
                text = state.commonNames.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {

                TextButton(onClick = {
                    val query = Uri.encode(state.plantName)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=$query"))
                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF007F4E)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Google", color = Color(0xFF007F4E))
                }

                /*TextButton(onClick = onGoogleClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF007F4E)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Google", color = Color(0xFF007F4E))
                }*/

                TextButton(onClick = onDetailsClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF007F4E)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Details", color = Color(0xFF007F4E))
                }
            }
        }
    } ?: run {
        // Se não tiver nada no estado
        Text("Nenhuma planta identificada.")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantIdentificationResultScreen() {
    val mockPlantName = "Adenium obesum"
    val mockCommonNames = listOf("desert-rose", "Mock Azalea", "impala lily")
    val mockProbability = 0.99
    val mockImageUrls = listOf(
        "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/d60/d603f30aac9292c673126a720eb7a48e01a9210e.jpg",
        "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/2b5/2b57a1161e841fbff8e9f91a58f99c5bb4a116a6.jpg"
    )

    PlantIdentificationResultScreen(
        onDetailsClick = { /* Preview: no-op */ }
    )
}


/**/
