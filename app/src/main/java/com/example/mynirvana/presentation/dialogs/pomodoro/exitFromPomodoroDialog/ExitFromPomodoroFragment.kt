package com.example.mynirvana.presentation.dialogs.pomodoro.exitFromPomodoroDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentExitFromPomodoroBinding
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.ExitFromPomodoroFragmentCallback

class ExitFromPomodoroFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    fun provideCallback(callback: ExitFromPomodoroFragmentCallback) {
        this.callback = callback
    }

    private lateinit var binding: FragmentExitFromPomodoroBinding
    private lateinit var callback: ExitFromPomodoroFragmentCallback

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
                callback.sendUserChoiceFromExitFromPomodoroFragment(false)
                this@ExitFromPomodoroFragment.dismiss()
            }

            endPomodoroTimerButton.setOnClickListener {
                callback.sendUserChoiceFromExitFromPomodoroFragment(true)
                this@ExitFromPomodoroFragment.dismiss()
            }
        }
    }


}