package com.example.mynirvana.presentation.mainFragments.meditationFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynirvana.databinding.FragmentMeditationBinding


class MeditationFragment : Fragment() {

    private lateinit var binding: FragmentMeditationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationBinding.inflate(inflater)
        return binding.root
    }


}