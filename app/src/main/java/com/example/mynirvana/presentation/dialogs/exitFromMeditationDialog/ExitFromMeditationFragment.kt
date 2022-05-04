package com.example.mynirvana.presentation.dialogs.exitFromMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromMeditationBinding
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback


class ExitFromMeditationFragment :
    DialogFragment() {

    companion object {
        var isDialogResumed: Boolean = false
    }

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
        isDialogResumed = true
        binding = FragmentExitFromMeditationBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            endMeditationButton.setOnClickListener {
                exitFromMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(true)
                this@ExitFromMeditationFragment.dismiss()
            }
            crossButtonInExitDialog.setOnClickListener {
                isDismissedByCrossButton = true
                this@ExitFromMeditationFragment.dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        exitFromMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed(
            isDismissedByCrossButton
        )
        isDialogResumed = false
        super.onDismiss(dialog)
    }


}