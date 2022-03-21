package com.example.mynirvana.presentation.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentHomeBinding
import com.example.mynirvana.presentation.meditation_buttons_recycler.MeditationButtonRecyclerAdapter
import com.example.mynirvana.presentation.meditation_buttons_recycler.SideSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var meditationButtonAdapter: MeditationButtonRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)


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
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SideSpacingItemDecoration(60))

            meditationButtonAdapter = MeditationButtonRecyclerAdapter()
            adapter = meditationButtonAdapter


        }

    }

}