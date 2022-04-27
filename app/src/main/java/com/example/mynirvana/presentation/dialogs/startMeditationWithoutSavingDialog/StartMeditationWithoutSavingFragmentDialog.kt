package com.example.mynirvana.presentation.dialogs.startMeditationWithoutSavingDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartMeditaitonWithoutSavingDialogBinding
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback


class StartMeditationWithoutSavingFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentStartMeditaitonWithoutSavingDialogBinding
    private lateinit var startMeditationWithoutSavingMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback
    private lateinit var meditation: Meditation
    private var isDismissedByCrossButton: Boolean = false


    fun provideCallBack(startMeditationWithoutSavingMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback) {
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
        binding = FragmentStartMeditaitonWithoutSavingDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startAndSaveMeditationButton.setOnClickListener {
            startMeditationWithoutSavingMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(
                true
            )
            this.dismiss()
        }

        binding.startAndDontSaveMeditationButton.setOnClickListener {
            startMeditationWithoutSavingMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(
                false
            )
            this.dismiss()
        }

        binding.crossButtonInStartAndSaveMeditationFragment.setOnClickListener {
            isDismissedByCrossButton = true
            this.dismiss()
        }


    }

    override fun onDismiss(dialog: DialogInterface) {
        startMeditationWithoutSavingMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed(
            isDismissedByCrossButton
        )
        super.onDismiss(dialog)
    }


}