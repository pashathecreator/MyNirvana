package com.skelrath.mynirvana.presentation.dialogs.pomodoro.startPomodoroDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentStartPomodoroBinding

class StartPomodoroFragment : DialogFragment() {

    private lateinit var binding: FragmentStartPomodoroBinding
    private lateinit var pomodoroName: String
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    fun providePomodoroName(pomodoroName: String) {
        this.pomodoroName = pomodoroName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartPomodoroBinding.inflate(inflater)
        initFunctional()
        return binding.root
    }

    private fun initFunctional() {
        with(binding) {
            crossButtonInPomodoroStart.setOnClickListener {
                functionToLaunch?.let { function ->
                    function(false)
                }
                this@StartPomodoroFragment.dismiss()
            }

            startPomodoroTimerButton.setOnClickListener {
                functionToLaunch?.let { function ->
                    function(true)
                }
                this@StartPomodoroFragment.dismiss()
            }

            sureForPomodoroTV.text = "Вы уверены что хотите начать помодоро\n\"$pomodoroName\""

        }
    }

    override fun onCancel(dialog: DialogInterface) {
        binding.crossButtonInPomodoroStart.performClick()
    }
}