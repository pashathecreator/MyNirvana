package com.example.mynirvana.presentation.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentHomeBinding
import com.example.mynirvana.presentation.meditation_buttons_recycler.MeditationButtonRecyclerAdapter
import com.example.mynirvana.presentation.meditation_buttons_recycler.SideSpacingItemDecoration

class HomeFragment : Fragment() {

    private lateinit var meditationButtonAdapter: MeditationButtonRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        homeFragmentViewModel =
            ViewModelProvider(requireActivity())[HomeFragmentViewModel::class.java]

        initRecyclerView(binding = binding)
        addDataSetToReadyMeditationButtons()



        return binding.root
    }


    private fun addDataSetToReadyMeditationButtons() {
        val data = homeFragmentViewModel.getReadyMeditations()
        meditationButtonAdapter.submitList(data)
    }


    private fun initRecyclerView(binding: FragmentHomeBinding) {
        binding.readyMeditationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SideSpacingItemDecoration(8))

            meditationButtonAdapter = MeditationButtonRecyclerAdapter()
            adapter = meditationButtonAdapter


        }

    }

}