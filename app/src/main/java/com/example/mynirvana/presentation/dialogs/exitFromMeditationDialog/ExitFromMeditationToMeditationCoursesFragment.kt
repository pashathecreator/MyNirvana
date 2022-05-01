package com.example.mynirvana.presentation.dialogs.exitFromMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromMeditationBinding
import com.example.mynirvana.databinding.FragmentExitFromMeditationToMeditationCoursesBinding
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback

class ExitFromMeditationToMeditationCoursesFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentExitFromMeditationToMeditationCoursesBinding
    private lateinit var exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback
    private var isDismissedByCrossButton: Boolean = false

    fun provideCallback(exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback) {
        this.exitFromMeditationFragmentDialogCallback =
            exitFromMeditationFragmentDialogCallback
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitFromMeditationToMeditationCoursesBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            endMeditationButtonInCourses.setOnClickListener {
                exitFromMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(true)
                this@ExitFromMeditationToMeditationCoursesFragment.dismiss()
            }
            binding.crossButtonInExitToCoursesDialog.setOnClickListener {
                isDismissedByCrossButton = true
                this@ExitFromMeditationToMeditationCoursesFragment.dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        exitFromMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed(
            isDismissedByCrossButton
        )
        super.onDismiss(dialog)
    }


}