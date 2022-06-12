package com.skelrath.mynirvana.presentation.dialogs.pomodoro.savePomodoroAndStartDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentSavePomodoroAndStartBinding

class SavePomodoroAndStartFragment : DialogFragment() {

    private lateinit var binding: FragmentSavePomodoroAndStartBinding
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
        binding = FragmentSavePomodoroAndStartBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            startSavedPomodoro.setOnClickListener {
                functionToLaunch?.let { function -> function(true) }
                this@SavePomodoroAndStartFragment.dismiss()
            }

            backToProductivityScreenButton.setOnClickListener {
                functionToLaunch?.let { function -> function(false) }
                this@SavePomodoroAndStartFragment.dismiss()
            }
        }
    }


}