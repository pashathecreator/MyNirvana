package com.example.mynirvana.presentation.dialogs.saveMeditationAndStartIt

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSaveMeditationAndStartDialogBinding
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.meditationCreatorActivity.SaveMeditationAndStartCallback
import com.example.mynirvana.presentation.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback


class SaveMeditationAndStartFragmentDialog : DialogFragment() {
    private lateinit var binding: FragmentSaveMeditationAndStartDialogBinding
    private lateinit var saveMeditationAndStartCallback: SaveMeditationAndStartCallback
    private lateinit var currentMeditation: Meditation


    fun provideCallback(saveMeditationAndStartCallback: SaveMeditationAndStartCallback) {
        this.saveMeditationAndStartCallback = saveMeditationAndStartCallback
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveMeditationAndStartDialogBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMainScreenButton.setOnClickListener {
            saveMeditationAndStartCallback.dialogAskForStartMeditation(false)
            this.dismiss()
        }

        binding.crossButtonInSaveMeditationAndStart.setOnClickListener {
            this.dismiss()
        }

        binding.startSavedMeditation.setOnClickListener {
            saveMeditationAndStartCallback.dialogAskForStartMeditation(true)
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        saveMeditationAndStartCallback.dialogDismiss()
        super.onDismiss(dialog)
    }


}