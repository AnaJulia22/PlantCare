package com.example.plantcare.samples.generators

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.plantcare.models.Plant
import kotlin.random.Random

fun generateRandomPlants(
    amountTasks: Int = 1
) = List(amountTasks) {
    val index = it + 1
    Plant(
        name = generateLoremIpsum(index),
        species = generateLoremIpsum(index * index),
        wateringFrequency = generateLoremIpsum(index * index * index),
        isWatered = index.mod(2) == 0,
        lastWatered = null,
        nextWatering = generateLoremIpsum(index * index * index * index),
        imageRes = null
    )
}

fun generateLoremIpsum(
    amountWords: Int = 1
) = LoremIpsum(
    Random.nextInt(1, if (amountWords > 1) amountWords else 2)
).values.first()