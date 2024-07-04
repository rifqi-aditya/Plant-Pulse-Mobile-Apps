package com.rifqi.plantpulse.ui.plant

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rifqi.plantpulse.R
import com.rifqi.plantpulse.data.model.Plant
import com.rifqi.plantpulse.databinding.ActivityPlantBinding
import com.rifqi.plantpulse.ui.ViewModelFactory

class PlantActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPlantBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PlantViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBackButton()

        val plant: Plant? = intent.getParcelableExtra("plant")

        plant?.let {
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivPlantImage)
            binding.tvPlantName.text = it.name
            binding.tvPlantStatus.text = it.healthStatus
            binding.tvSoilMoisturePercentage.text = getString(
                R.string.percentage_format,
                it.soilMoisture.toInt()
            )
        }

        viewModel.sensors.observe(this) { sensors ->
            val dht11Data = sensors.find { it.id == "dht11" }
            val hcSr04Data = sensors.find { it.id == "hc_sr04" }
            val ldrData = sensors.find { it.id == "ldr" }

            // Example: Update UI with specific sensor data
            dht11Data?.let {
                binding.tvHumidityPercentage.text =
                    getString(R.string.percentage_format, it.humidity?.toInt())
                binding.tvTemperature.text = getString(R.string.temperature_format, it.temperature)
            }

            hcSr04Data?.let {
                // Update UI with hcSr04Data
                // Example: binding.tvHcSr04Percentage.text = it.percentage.toString()
            }

            ldrData?.let {
                binding.tvSunlightPercentage.text =
                    getString(R.string.percentage_format, it.percentage?.toInt())
            }
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val TAG = "PlantActivity"
    }
}