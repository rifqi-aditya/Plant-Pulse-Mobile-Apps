package com.rifqi.plantpulse.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.plantpulse.R
import com.rifqi.plantpulse.databinding.FragmentHomeBinding
import com.rifqi.plantpulse.ui.ViewModelFactory
import com.rifqi.plantpulse.ui.adapter.PlantsAdapter

class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupWaterReservoirsBar()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvPlants
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PlantsAdapter()

        viewModel.getPlantsLiveData().observe(viewLifecycleOwner) { plants ->
            (binding.rvPlants.adapter as PlantsAdapter).submitList(plants)
        }
    }

    private fun setupWaterReservoirsBar() {
        val dots = listOf(
            binding.dot1, binding.dot2, binding.dot3, binding.dot4, binding.dot5, binding.dot6
        )
        // Memantau perubahan data sensor
        viewModel.sensors.observe(viewLifecycleOwner) { sensors ->
            val hcSr04Data = sensors.find { it.id == "hc_sr04" }

            hcSr04Data?.let { sensor ->
                val percentage = sensor.percentage?.coerceIn(0.0, 100.0)
                if (percentage != null) {
                    binding.tvWaterReservoirsPercentage.text =
                        getString(R.string.percentage_format, percentage.toInt())
                    val activeDots = (percentage / 20).toInt()

                    for (i in dots.indices) {
                        dots[i].setBackgroundResource(
                            if (i <= activeDots) R.drawable.ic_circle_green else R.drawable.ic_circle_gray
                        )
                    }
                } else {
                    Log.w(TAG, "Percentage value is null or out of bounds")
                }
            } ?: run {
                Log.w(TAG, "HC-SR04 sensor data not found")
            }
        }
    }

    private fun showLowWaterNotification() {
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Water Reservoir Low")
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentText("Water reservoir is less than or equal to 20%. Refill needed.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText("Reminder")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }


    companion object {
        private const val TAG = "HomeFragment"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "water_reservoir_channel"
        private const val CHANNEL_NAME = "Water Reservoir Notifications"
    }
}