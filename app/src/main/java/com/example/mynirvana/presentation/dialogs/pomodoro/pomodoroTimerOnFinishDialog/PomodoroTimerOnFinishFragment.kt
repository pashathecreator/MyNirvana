package com.example.mynirvana.presentation.dialogs.pomodoro.pomodoroTimerOnFinishDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentPomodoroTimerOnFinishBinding

class PomodoroTimerOnFinishFragment : DialogFragment() {

    private lateinit var binding: FragmentPomodoroTimerOnFinishBinding
    private var functionToLaunch: (() -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: () -> Unit) {
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
        binding = FragmentPomodoroTimerOnFinishBinding.inflate(inflater)
        initBackToFragmentButton()
        return binding.root
    }

    private fun initBackToFragmentButton() {
        binding.backToProductivityFragment.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        functionToLaunch?.let { it() }
        isDialogResumed = false
        super.onDismiss(dialog)
    }
}