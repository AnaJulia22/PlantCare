package com.example.plantcare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.plantcare.models.Plant
import java.util.concurrent.TimeUnit

/*
fun scheduleWateringReminderWorker(
    context: Context,
    ) {
    val workRequest = PeriodicWorkRequest.Builder(
        WateringReminderWorker::class.java,
        1, TimeUnit.DAYS // Executa diariamente
    ).build()

    WorkManager.getInstance(context).enqueue(workRequest)
}*/

fun scheduleWateringReminder(plant: Plant, context: Context) {
    val data = workDataOf(
        "plantId" to plant.id,
        "plantName" to plant.name
    )

    // Calcular a próxima data de rega
    val nextWateringDate = calculateNextWateringDate(plant)
    val delayInMillis = nextWateringDate - System.currentTimeMillis()

    val reminderRequest = OneTimeWorkRequestBuilder<WateringReminderWorker>()
        .setInputData(data)
        .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueue(reminderRequest)
}

private fun calculateNextWateringDate(plant: Plant): Long {
    val lastWatered = plant.lastWatered?.toLongOrNull() ?: System.currentTimeMillis()
    val frequencyInMillis = plant.wateringFrequency.toLongOrNull()?.times(24L * 60L * 60L * 1000L) ?: 0L
    return lastWatered + frequencyInMillis
}

class WateringReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val plantId = inputData.getString("plantId") ?: return Result.failure()
        val plantName = inputData.getString("plantName") ?: return Result.failure()

        // Criar e mostrar a notificação
        val notificationManager = NotificationManagerCompat.from(applicationContext)

        // Criar o canal de notificação (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "watering_channel",
                "Lembretes de Rega",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "watering_channel")
            .setSmallIcon(R.drawable.plant_icon)
            .setContentTitle("Hora de regar!")
            .setContentText("Sua planta $plantName precisa de água.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        //notificationManager.notify(plantId.hashCode(), notification)
        // Check for permission before posting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(plantId.hashCode(), notification)
            } else {
                // Permission not granted, handle it (e.g., log, show a message)
                // You should request the permission in an Activity or Fragment
                // and not in a Worker
            }
        } else {
            // No need to check for permission on older Android versions
            notificationManager.notify(plantId.hashCode(), notification)
        }
        return Result.success()
    }
}
