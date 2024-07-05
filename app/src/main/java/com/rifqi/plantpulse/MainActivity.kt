package com.rifqi.plantpulse

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.rifqi.plantpulse.databinding.ActivityMainBinding
import com.rifqi.plantpulse.service.WaterReservoirService

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setupFirebaseAuth()
        startWaterReservoirService()
    }

    private fun setupFirebaseAuth() {
        val auth = FirebaseAuth.getInstance()
        val email = "aditya.rifqi30@gmail.com"
        val password = "salamander"
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // setupFirebaseListeners()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startWaterReservoirService() {
        val serviceIntent = Intent(this, WaterReservoirService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopWaterReservoirService()
    }

    private fun stopWaterReservoirService() {
        val serviceIntent = Intent(this, WaterReservoirService::class.java)
        stopService(serviceIntent)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}