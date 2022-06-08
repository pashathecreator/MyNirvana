package com.example.mynirvana.presentation.dialogs.pomodoro.exitFromPomodoroDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromPomodoroBinding

class ExitFromPomodoroFragment : DialogFragment() {

    private lateinit var binding: FragmentExitFromPomodoroBinding
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitFromPomodoroBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            crossButtonInExitPomodoroDialog.setOnClickListener {
                functionToLaunch?.let { function -> function(false) }
                this@ExitFromPomodoroFragment.dismiss()
            }

            endPomodoroTimerButton.setOnClickListener {
                functionToLaunch?.let { function -> function(true) }
                this@ExitFromPomodoroFragment.dismiss()
            }
        }
    }
}