package com.example.plantcare.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantcare.data.remote.dto.Description
import com.example.plantcare.data.remote.dto.Details
import com.example.plantcare.data.remote.dto.Image
import com.example.plantcare.ui.states.PlantIdentifierUiState
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import com.example.plantcare.ui.viewmodels.PlantResultViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantDetailsScreen(
    viewModel: PlantIdentifierViewModel = koinViewModel()
) {
    val details = viewModel.uiState.collectAsState().value

    PlantDetailsContent(details = details)
}

@Composable
fun PlantDetailsContent(details: PlantIdentifierUiState?) {
    details?.let {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            details.commonNames?.takeIf { it.isNotEmpty() }?.let { names ->
                Text(
                    text = "Nomes comuns",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = names.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            details.plantUrl?.let { url ->
                Text(
                    text = "Mais informações",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                val annotatedUrl = buildAnnotatedString {
                    pushStringAnnotation(tag = "URL", annotation = url)
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF007F4E),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(url)
                    }
                    pop()
                }

                Text(
                    text = annotatedUrl,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            details.description?.let { desc ->
                Text(
                    text = "Descrição",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = desc ?: "-",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            details.bestWatering?.let {
                Text(
                    text = "Rega ideal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            details.toxicity?.let {
                Text(
                    text = "Toxicidade",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (it.lowercase().contains("toxic")) Color.Red else Color.Black
                )
            }
        }
    } ?: Text("Sem detalhes disponíveis")
}


@Preview(showBackground = true)
@Composable
fun PreviewPlantDetailsScreen() {
    val mockUiState = PlantIdentifierUiState(
        plantName = "Desert Rose",
        plantImage = "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/d60/d603f30aac9292c673126a720eb7a48e01a9210e.jpg",
        probability = 0.95,
        description = "Adenium obesum, also known as the desert rose, is a species of flowering plant in the dogbane family.",
        commonNames = listOf("desert-rose", "Mock Azalea", "impala lily"),
        toxicity = "Toxic to pets and humans if ingested.",
        plantUrl = "https://en.wikipedia.org/wiki/Adenium_obesum",
        bestWatering = "Allow soil to dry out between watering. Water sparingly in winter."
    )

    PlantDetailsContent(details = mockUiState)
}
