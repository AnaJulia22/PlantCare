package com.example.plantcare.api

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.example.plantcare.data.remote.dto.PlantRequest
import com.example.plantcare.data.remote.dto.PlantResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PlantIdService {
    @POST("identification")
    suspend fun identifyPlant(
        @Header("Api-Key") apiKey: String,
        @Body request: PlantRequest,
        @Query("details") details: String
    ): Response<PlantResponse>
}

fun getRetrofit(): PlantIdService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://plant.id/api/v3/")

            .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(PlantIdService::class.java)
}
