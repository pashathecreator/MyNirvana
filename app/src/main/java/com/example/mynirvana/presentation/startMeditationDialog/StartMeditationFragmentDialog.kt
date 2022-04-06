package com.example.mynirvana.presentation.startMeditationDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartMeditationDialogBinding
import com.example.mynirvana.presentation.getUserChoiceFromDialogCallback.StartMeditationFragmentDialogCallback


class StartMeditationFragmentDialog() :
    DialogFragment() {

    companion object {
        var meditationName = "MEDITATION_NAME"
    }

    private lateinit var binding: FragmentStartMeditationDialogBinding
    private lateinit var startMeditationFragmentDialogCallback: StartMeditationFragmentDialogCallback

    fun provideCallback(startMeditationFragmentDialogCallback: StartMeditationFragmentDialogCallback) {
        this.startMeditationFragmentDialogCallback = startMeditationFragmentDialogCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMeditationDialogBinding.inflate(inflater)

        binding.sureForMeditationTV.text = "Начать медитацию ${meditationName}?"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.crossButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoice(false)

        }

        binding.startMeditionButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoice(true)
            onDestroy()
        }
    }
}