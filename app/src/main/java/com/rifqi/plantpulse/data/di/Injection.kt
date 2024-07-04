package com.rifqi.plantpulse.data.di

import android.content.Context
import com.rifqi.plantpulse.data.repository.PlantRepository
import com.rifqi.plantpulse.data.repository.SensorRepository

object Injection {
    fun providePlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(context)
    }
    fun provideSensorRepository(context: Context): SensorRepository {
        return SensorRepository.getInstance(context)
    }
}