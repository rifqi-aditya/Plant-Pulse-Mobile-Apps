package com.rifqi.plantpulse
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rifqi.plantpulse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Firebase Authentication instance
    private val auth = FirebaseAuth.getInstance()
    private val email = "aditya.rifqi30@gmail.com"
    private val password = "salamander"

    // Firebase Database references
    private val database = FirebaseDatabase.getInstance()
    private val humidityRef = database.getReference("sensor_data/humidity")
    private val soilMoistureRef = database.getReference("sensor_data/soil_moisture")
    private val temperatureRef = database.getReference("sensor_data/temperature")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    setupFirebaseListeners()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

//    private fun setupFirebaseListeners() {
//        // Listener untuk mendapatkan data humidity
//        humidityRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val humidity = dataSnapshot.getValue(Float::class.java)
//                if (humidity != null) {
//                    Log.d(TAG, "Humidity is: $humidity%")
//                    val humidityText = getString(R.string.humidity_text, humidity)
//                    binding.tvHumidity.text = humidityText
//                } else {
//                    Log.e(TAG, "Failed to parse humidity value.")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read humidity value.", error.toException())
//            }
//        })
//
//        // Listener untuk mendapatkan data soil moisture
//        soilMoistureRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val soilMoisture = dataSnapshot.getValue(Float::class.java)
//                if (soilMoisture != null) {
//                    Log.d(TAG, "Soil Moisture is: $soilMoisture%")
//                    val soilMoistureText = getString(R.string.soil_moisture_text, soilMoisture)
//                    binding.tvSoilMoisture.text = soilMoistureText
//                } else {
//                    Log.e(TAG, "Failed to parse soil moisture value.")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read soil moisture value.", error.toException())
//            }
//        })
//
//        // Listener untuk mendapatkan data temperature
//        temperatureRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val temperature = dataSnapshot.getValue(Float::class.java)
//                if (temperature != null) {
//                    Log.d(TAG, "Temperature is: $temperature°C")
//                    val temperatureText = getString(R.string.temperature_text, temperature)
//                    binding.tvTemperature.text = temperatureText
//                } else {
//                    Log.e(TAG, "Failed to parse temperature value.")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read temperature value.", error.toException())
//            }
//        })
//    }

    companion object {
        private const val TAG = "com.rifqi.plantpulse.MainActivity"
    }
}