package com.rifqi.plantpulse.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvPlants
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PlantsAdapter()

        viewModel.getPlantsLiveData().observe(viewLifecycleOwner) { plants ->
            (binding.rvPlants.adapter as PlantsAdapter).submitList(plants)
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}