package com.example.plantcare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.plantcare.models.Plant

private fun sendNotification(context: Context, plant: Plant) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Crie um canal de notificação (necessário para Android 8.0+)
    val channelId = "watering_reminder_channel"
    val channel = NotificationChannel(
        channelId,
        "Watering Reminder",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    notificationManager.createNotificationChannel(channel)

    // Crie a notificação
    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Hora de regar sua planta!")
        .setContentText("Não se esqueça de regar sua planta ${plant.name}.")
        .setSmallIcon(R.drawable.plant_icon) // Ícone da notificação
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    // Exiba a notificação
    notificationManager.notify(plant.id.hashCode(), notification)
}