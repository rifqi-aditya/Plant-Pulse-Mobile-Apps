package com.rifqi.plantpulse.ui.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rifqi.plantpulse.R
import com.rifqi.plantpulse.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {

    private val binding by lazy {
        FragmentFirstScreenBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
        }
    }
}