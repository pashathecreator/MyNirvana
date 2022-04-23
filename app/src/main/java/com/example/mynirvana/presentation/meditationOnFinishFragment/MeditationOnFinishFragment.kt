package com.example.mynirvana.presentation.meditationOnFinishFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentMeditationOnFinishBinding
import com.example.mynirvana.presentation.meditationTimerActivity.MeditationOnFinishFragmentCallback

class MeditationOnFinishFragment : DialogFragment() {
    private lateinit var binding: FragmentMeditationOnFinishBinding
    private lateinit var callback: MeditationOnFinishFragmentCallback

    fun provideCallback(callback: MeditationOnFinishFragmentCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationOnFinishBinding.inflate(inflater)

        return binding.root
    }

}