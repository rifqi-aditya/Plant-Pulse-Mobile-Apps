package com.rifqi.plantpulse.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.rifqi.plantpulse.R
import com.rifqi.plantpulse.data.model.Sensor
import com.rifqi.plantpulse.data.repository.SensorRepository

class WaterReservoirService : Service() {

    private lateinit var sensorRepository: SensorRepository
    private lateinit var sensorLiveData: LiveData<List<Sensor>>

    private val sensorObserver = Observer<List<Sensor>> { sensors ->
        sensors.let {
            val hcSr04Data = sensors.find { it.id == "hc_sr04" }
            hcSr04Data?.let { sensor ->
                val percentage = sensor.percentage?.coerceIn(0.0, 100.0)
                if (percentage != null && percentage <= 20) {
                    showLowWaterNotification()
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        sensorRepository = SensorRepository()
        sensorLiveData = sensorRepository.getSensors()

        sensorLiveData.observeForever(sensorObserver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorLiveData.removeObserver(sensorObserver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showLowWaterNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
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
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        startForeground(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "water_reservoir_channel"
        private const val CHANNEL_NAME = "Water Reservoir Notifications"
    }
}