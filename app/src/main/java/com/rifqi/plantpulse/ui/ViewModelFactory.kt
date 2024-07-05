package com.rifqi.plantpulse.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rifqi.plantpulse.data.di.Injection
import com.rifqi.plantpulse.data.repository.PlantRepository
import com.rifqi.plantpulse.data.repository.SensorRepository
import com.rifqi.plantpulse.ui.home.HomeViewModel
import com.rifqi.plantpulse.ui.plant.PlantViewModel

class ViewModelFactory(private val plantRespository: PlantRepository, private val sensorRepository: SensorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(plantRespository, sensorRepository) as T
            }
            modelClass.isAssignableFrom(PlantViewModel::class.java) -> {
                PlantViewModel(sensorRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.providePlantRepository(context),
                        Injection.provideSensorRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}