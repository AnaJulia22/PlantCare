package com.example.plantcare.Application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.plantcare.MainActivity
import com.example.plantcare.R
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.database.PlantDataBase
import com.example.plantcare.database.dao.PlantDao
import com.example.plantcare.database.entities.PlantEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.forEach

class PlantWateringScheduler (context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val plantName = inputData.getString("PLANT_NAME") ?: "Sua planta"

        sendNotification(applicationContext, plantName)

        return Result.success()
    }

    private fun sendNotification(context: Context, plantName: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "watering_reminder"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Watering Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(
                R.drawable.plant_icon
            )
            .setContentTitle("Hora de regar sua planta!")
            .setContentText("Não se esqueça de regar ${plantName} hoje.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(plantName.hashCode(), notification)
    }

}