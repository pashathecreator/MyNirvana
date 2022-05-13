package com.example.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromMeditationToMeditationCoursesBinding
import com.example.mynirvana.presentation.dialogs.meditation.userChoiceCallback.UserChoiceAboutMeditationDialogCallback

class ExitFromMeditationToMeditationCoursesFragment : DialogFragment() {


    companion object {
        var isDialogResumed: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentExitFromMeditationToMeditationCoursesBinding
    private lateinit var exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationDialogCallback
    private var isDismissedByCrossButton: Boolean = false

    fun provideCallback(exitFromMeditationFragmentDialogCallback: UserChoiceAboutMeditationDialogCallback) {
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
                exitFromMeditationFragmentDialogCallback.sendUserChoiceFromMeditationStartDialog(true)
                this@ExitFromMeditationToMeditationCoursesFragment.dismiss()
            }
            binding.crossButtonInExitToCoursesDialog.setOnClickListener {
                exitFromMeditationFragmentDialogCallback.sendUserChoiceFromMeditationStartDialog(false)
                this@ExitFromMeditationToMeditationCoursesFragment.dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        isDialogResumed = false
        super.onDismiss(dialog)
    }


}