package com.rifqi.plantpulse.data.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.rifqi.plantpulse.data.model.Sensor
import kotlinx.coroutines.tasks.await

class SensorRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getSensors(): List<Sensor> {
        val sensors = mutableListOf<Sensor>()

        try {
            val querySnapshot = firestore.collection("sensors").get().await()
            for (document in querySnapshot.documents) {
                val id = document.id
                val humidity = document.getDouble("humidity")
                val temperature = document.getDouble("temperature")
                val percentage = document.getDouble("percentage")

                val sensor = Sensor(id, humidity, temperature, percentage)
                sensors.add(sensor)
            }
        } catch (e: Exception) {
            // Handle exceptions
            e.printStackTrace()
        }

        return sensors
    }

    companion object {
        private const val TAG = "SensorRepository"

        @Volatile
        private var instance: SensorRepository? = null
        fun getInstance(context: Context): SensorRepository =
            instance ?: synchronized(this) {
                instance ?: SensorRepository()
            }.also { instance = it }
    }
}