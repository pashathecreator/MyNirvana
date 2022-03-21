package com.example.mynirvana.presentation.new_meditation_settings_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynirvana.R
import com.example.mynirvana.databinding.MeditationCreationFragmentBinding

class MeditationCreationFragment : Fragment() {

    private lateinit var viewModel: MeditationCreationViewModel
    private lateinit var binding: MeditationCreationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MeditationCreationFragmentBinding.inflate(inflater)


        return binding.root
    }


}