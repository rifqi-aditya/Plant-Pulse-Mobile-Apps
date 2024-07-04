package com.rifqi.plantpulse.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant(
    val name: String = "",
    val healthStatus: String = "",
    val soilMoisture: Double = 0.0,
    val imageUrl: String = "",
) : Parcelable
