package com.example.mynirvana.presentation.dialogs.exitFromMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromMeditationBinding
import com.example.mynirvana.presentation.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback


class ExitFromMeditationFragment() :
    DialogFragment() {

    private lateinit var binding: FragmentExitFromMeditationBinding
    private lateinit var exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback
    private var isDismissedByCrossButton: Boolean = false

    fun provideCallback(exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback) {
        this.exitFromMeditationFragmentDialogCallback =
            exitFromMeditationFragmentDialogCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitFromMeditationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.endMeditationButton.setOnClickListener {
            exitFromMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(true)
            this.dismiss()
        }
        binding.crossButtonInExitDialog.setOnClickListener {
            isDismissedByCrossButton = true
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        exitFromMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed(
            isDismissedByCrossButton
        )
        super.onDismiss(dialog)
    }


}