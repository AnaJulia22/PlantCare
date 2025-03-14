package com.example.plantcare

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleWateringReminderWorker(context: Context) {
    val workRequest = PeriodicWorkRequest.Builder(
        WateringReminderWorker::class.java,
        1, TimeUnit.DAYS // Executa diariamente
    ).build()

    WorkManager.getInstance(context).enqueue(workRequest)
}