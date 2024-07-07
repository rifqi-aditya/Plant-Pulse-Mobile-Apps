package com.rifqi.plantpulse

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

// Memberitahu bahwa pekerjaan telah selesai
class WaterLevelWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Periksa data level air dari Firestore
        checkWaterLevel()
        return Result.success()
    }

    private fun checkWaterLevel() {
        val db = Firebase.firestore
        db.collection("sensors").document("hc_sr04")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val percentage = document.getDouble("percentage")?.coerceIn(0.0, 100.0)
                    if (percentage != null && percentage <= 20) {
                        showLowWaterNotification()
                    }
                }
            }
    }

    private fun showLowWaterNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Water Reservoir Low")
            .setSmallIcon(R.drawable.sublogo_plantpulse)
            .setContentText("Water reservoir is less than or equal to 20%. Refill needed.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText("Reminder")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "water_reservoir_channel"
        private const val CHANNEL_NAME = "Water Reservoir Notifications"
    }

}