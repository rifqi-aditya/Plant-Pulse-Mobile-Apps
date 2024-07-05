package com.rifqi.plantpulse.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.rifqi.plantpulse.data.model.Sensor

class SensorRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val sensorsCollection = firestore.collection("sensors")

    fun getSensors(): LiveData<List<Sensor>> {
        val liveData = MutableLiveData<List<Sensor>>()

        sensorsCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w(TAG, "Listen failed", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val sensorsList = mutableListOf<Sensor>()
                for (document in snapshot.documents) {
                    val id = document.id
                    val humidity = document.getDouble("humidity")
                    val temperature = document.getDouble("temperature")
                    val percentage = document.getDouble("percentage")

                    val sensor = Sensor(id, humidity, temperature, percentage)
                    sensorsList.add(sensor)
                }
                liveData.value = sensorsList
            } else {
                liveData.value = emptyList()
            }
        }

        return liveData
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