package com.rifqi.plantpulse.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqi.plantpulse.data.model.Plant
import com.rifqi.plantpulse.data.repository.PlantRepository
import com.rifqi.plantpulse.data.repository.SensorRepository

class HomeViewModel(
    private val plantRepository: PlantRepository,
    private val sensorRepository: SensorRepository
) : ViewModel() {
    fun getPlantsLiveData(): LiveData<List<Plant>> = plantRepository.getPlants()

    val sensors = sensorRepository.getSensors()
}