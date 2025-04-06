package com.example.plantcare.data.remote.dto

data class Suggestion(
    val id: String?,
    val name: String?,
    val probability: Double?,
    val similar_images: List<SimilarImage>?,
    val details: Details?
)

data class Details(
    val image: Image?,
    val language: String?,
    val entity_id: String?,
    val common_names: List<String>?,
    val url: String?,
    val description_all: Description?,
    val best_watering: String?,
    val toxicity: String?
)

data class Image(
    val value: String?,
    val citation: String?,
    val license_name: String?,
    val license_url: String?
)

data class Description(
    val value: String?,
    val source: String?
)