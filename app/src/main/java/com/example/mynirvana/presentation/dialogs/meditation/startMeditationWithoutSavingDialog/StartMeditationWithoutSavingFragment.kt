package com.example.mynirvana.presentation.dialogs.meditation.startMeditationWithoutSavingDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartMeditaitonWithoutSavingBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.presentation.dialogs.meditation.userChoiceCallback.UserChoiceAboutMeditationDialogCallback


class StartMeditationWithoutSavingFragment : DialogFragment() {

    private lateinit var binding: FragmentStartMeditaitonWithoutSavingBinding
    private lateinit var startMeditationWithoutSavingMeditationFragmentDialogCallback: UserChoiceAboutMeditationDialogCallback
    private lateinit var meditation: Meditation
    private var isDismissedByCrossButton: Boolean = false


    fun provideCallBack(startMeditationWithoutSavingMeditationFragmentDialogCallback: UserChoiceAboutMeditationDialogCallback) {
        this.startMeditationWithoutSavingMeditationFragmentDialogCallback =
            startMeditationWithoutSavingMeditationFragmentDialogCallback
    }

    fun provideMeditation(meditation: Meditation) {
        this.meditation = meditation
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMeditaitonWithoutSavingBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.startAndSaveMeditationButton.setOnClickListener {
            startMeditationWithoutSavingMeditationFragmentDialogCallback.sendUserChoiceFromMeditationStartDialog(
                true
            )
            this.dismiss()
        }

        binding.startAndDontSaveMeditationButton.setOnClickListener {
            startMeditationWithoutSavingMeditationFragmentDialogCallback.sendUserChoiceFromMeditationStartDialog(
                false
            )
            this.dismiss()
        }

        binding.crossButtonInStartAndSaveMeditationFragment.setOnClickListener {
            this.dismiss()
        }
    }
}