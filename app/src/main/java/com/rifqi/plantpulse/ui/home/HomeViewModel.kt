package com.rifqi.plantpulse.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqi.plantpulse.data.model.Plant
import com.rifqi.plantpulse.data.repository.PlantRepository

class HomeViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    fun getPlantsLiveData(): LiveData<List<Plant>> = plantRepository.getPlants()
}