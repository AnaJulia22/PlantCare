package com.example.plantcare

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.plantcare.models.Plant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WateringReminderWorker (
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Obtenha a lista de plantas do banco de dados ou do repositório
        val plants = getPlantsFromDatabase()

        // Verifique cada planta
        for (plant in plants) {
            if (!plant.isWatered && isWateringDay(plant.nextWatering)) {
                sendNotification(plant)
            }
        }

        return Result.success()
    }

    private fun isWateringDay(nextWatering: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val nextWateringDate = LocalDate.parse(nextWatering, formatter)
        val currentDate = LocalDate.now()
        return currentDate.isEqual(nextWateringDate)
    }

    private fun sendNotification(plant: Plant) {
        // Implemente a lógica para enviar uma notificação
        // Use NotificationManager ou uma biblioteca como NotificationCompat
    }

    private fun getPlantsFromDatabase(): List<Plant> {
        // Implemente a lógica para obter as plantas do banco de dados
        return emptyList()
    }
}