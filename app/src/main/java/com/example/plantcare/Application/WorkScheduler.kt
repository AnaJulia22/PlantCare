package com.example.plantcare.Application

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class WorkScheduler {

    companion object {
        fun scheduleWateringReminder(context: Context, selectedTime: LocalTime, plantName: String) {
            val workManager = WorkManager.getInstance(context)

            // Calculando o tempo até o próximo horário de rega

            val targetTime = LocalDate.now().atTime(selectedTime)
            val currentTime = LocalDate.now().atTime(LocalTime.now())

            // Se o horário selecionado já passou, agenda para o próximo dia
            var delay = targetTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if (delay < 0) {
                delay += TimeUnit.DAYS.toMillis(1) // Adiciona 24 horas se o horário já passou
            }

            // Criando o WorkRequest
            val workRequest = OneTimeWorkRequestBuilder<PlantWateringScheduler>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(workDataOf("plant_name" to plantName))
                .build()

            // Enviando o trabalho para o WorkManager
            workManager.enqueue(workRequest)
        }
    }
}