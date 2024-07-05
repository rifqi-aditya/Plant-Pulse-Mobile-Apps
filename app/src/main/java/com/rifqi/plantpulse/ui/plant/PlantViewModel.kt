package com.rifqi.plantpulse.ui.plant

import androidx.lifecycle.ViewModel
import com.rifqi.plantpulse.data.repository.SensorRepository

class PlantViewModel(private val sensorRepository: SensorRepository) : ViewModel() {
    val sensors = sensorRepository.getSensors()
}