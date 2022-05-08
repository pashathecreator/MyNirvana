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


    companion object {
        var isDialogResumed: Boolean = false
    }

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
        isDialogResumed = true
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
                exitFromMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(false)
                this@ExitFromMeditationToMeditationCoursesFragment.dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        exitFromMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed()
        isDialogResumed = false
        super.onDismiss(dialog)
    }


}