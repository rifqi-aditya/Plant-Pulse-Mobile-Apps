package com.rifqi.plantpulse.ui.plant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rifqi.plantpulse.databinding.ActivityPlantBinding

class PlantActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPlantBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide
            .with(this)
            .load("https://images.unsplash.com/photo-1596591868231-05e852efc96f?q=80&w=1992&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
            .into(binding.circleImageView)
    }
}