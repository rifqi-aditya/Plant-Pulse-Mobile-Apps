package com.rifqi.plantpulse.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.rifqi.plantpulse.data.model.Plant

class PlantRepository {
    private val firestore = Firebase.firestore
    private val plantsCollection = firestore.collection("plants")

    fun getPlants(): LiveData<List<Plant>> {
        val liveData = MutableLiveData<List<Plant>>()

        plantsCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w(TAG, "Listen failed", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val plantsList = mutableListOf<Plant>()
                for (document in snapshot.documents) {
                    val plant = document.toObject(Plant::class.java)
                    plant?.let { plantsList.add(it) }
                }
                liveData.value = plantsList
            } else {
                liveData.value = emptyList()
            }
        }

        return liveData
    }

    companion object {
        private const val TAG = "PlantRepository"

        @Volatile
        private var instance: PlantRepository? = null
        fun getInstance(context: Context): PlantRepository =
            instance ?: synchronized(this) {
                instance ?: PlantRepository()
            }.also { instance = it }
    }
}