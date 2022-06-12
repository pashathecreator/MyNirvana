package com.skelrath.mynirvana.presentation.dialogs.pomodoro.startPomodoroWithoutSavingDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentStartPomodoroWithoutSavingBinding


class StartPomodoroWithoutSavingFragment : DialogFragment() {

    private lateinit var binding: FragmentStartPomodoroWithoutSavingBinding
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
        binding = FragmentStartPomodoroWithoutSavingBinding.inflate(inflater)
        initButtons()

        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            crossButtonInStartAndSaveMeditationFragment.setOnClickListener {
                this@StartPomodoroWithoutSavingFragment.dismiss()
            }

            startAndSavePomodoroButton.setOnClickListener {
                functionToLaunch?.let { function -> function(true) }
                this@StartPomodoroWithoutSavingFragment.dismiss()
            }

            startAndDontSavePomodoroButton.setOnClickListener {
                functionToLaunch?.let { function -> function(false) }
                this@StartPomodoroWithoutSavingFragment.dismiss()
            }
        }
    }


}