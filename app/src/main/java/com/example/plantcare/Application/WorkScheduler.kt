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
        fun scheduleWateringReminder(context: Context, selectedTime: LocalTime, selectedDate: LocalDate, plantName: String) {
            val workManager = WorkManager.getInstance(context)

            val targetTime = selectedDate.atTime(selectedTime)
            val currentTime = LocalDate.now().atTime(LocalTime.now())

            var delay = targetTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if (delay < 0) {
                delay += TimeUnit.DAYS.toMillis(1)
            }

            val workRequest = OneTimeWorkRequestBuilder<PlantWateringScheduler>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(workDataOf("plant_name" to plantName))
                .build()

            workManager.enqueue(workRequest)
        }
    }
}