package com.example.mynirvana.presentation.startMeditationDialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartMeditationDialogBinding
import com.example.mynirvana.presentation.getUserChoiceFromDialogCallback.StartMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.homeFragment.HomeFragment


class StartMeditationFragmentDialog() :
    DialogFragment() {


    private lateinit var binding: FragmentStartMeditationDialogBinding
    private lateinit var startMeditationFragmentDialogCallback: StartMeditationFragmentDialogCallback
    private lateinit var meditationName: String

    fun provideCallback(startMeditationFragmentDialogCallback: StartMeditationFragmentDialogCallback) {
        this.startMeditationFragmentDialogCallback = startMeditationFragmentDialogCallback
    }

    fun provideMeditationName(meditationName: String) {
        this.meditationName = meditationName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMeditationDialogBinding.inflate(inflater)

        binding.sureForMeditationTV.text = "Начать медитацию $meditationName?"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.crossButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoice(false)
            this.dismiss()

        }

        binding.startMeditionButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoice(true)
            this.dismiss()


        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        startMeditationFragmentDialogCallback.fragmentDismissed()

        super.onDismiss(dialog)
    }
}