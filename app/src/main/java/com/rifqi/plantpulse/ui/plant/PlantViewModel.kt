package com.rifqi.plantpulse.ui.plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.plantpulse.data.model.Sensor
import com.rifqi.plantpulse.data.repository.SensorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel(private val sensorRepository: SensorRepository) : ViewModel() {
    private val _sensors = MutableLiveData<List<Sensor>>()
    val sensors: LiveData<List<Sensor>>
        get() = _sensors

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _sensors.postValue(sensorRepository.getSensors())
        }
    }
}