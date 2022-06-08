package com.example.mynirvana.presentation.dialogs.meditation.exitFromMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromMeditationBinding
import com.example.mynirvana.presentation.dialogs.meditation.userChoiceCallback.UserChoiceAboutMeditationDialogCallback


class ExitFromMeditationFragment : DialogFragment() {

    private lateinit var binding: FragmentExitFromMeditationBinding
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }


    companion object {
        var isDialogResumed: Boolean = false
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
                functionToLaunch?.let { function -> function(true) }
                this@ExitFromMeditationFragment.dismiss()
            }
            crossButtonInExitDialog.setOnClickListener {
                functionToLaunch?.let { function -> function(false) }
                this@ExitFromMeditationFragment.dismiss()
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        isDialogResumed = false
        super.onDismiss(dialog)
    }


}