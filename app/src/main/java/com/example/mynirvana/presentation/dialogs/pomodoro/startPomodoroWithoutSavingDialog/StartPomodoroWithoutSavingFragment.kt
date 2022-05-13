package com.example.mynirvana.presentation.dialogs.pomodoro.startPomodoroWithoutSavingDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartPomodoroWithoutSavingBinding
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.StartPomodoroWithoutSavingFragmentCallback


class StartPomodoroWithoutSavingFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    fun provideCallback(callback: StartPomodoroWithoutSavingFragmentCallback) {
        this.callback = callback
    }

    private lateinit var binding: FragmentStartPomodoroWithoutSavingBinding
    private lateinit var callback: StartPomodoroWithoutSavingFragmentCallback

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
                callback.sendUserChoiceFromStartPomodoroWithoutSavingFragment(true)
                this@StartPomodoroWithoutSavingFragment.dismiss()
            }

            startAndDontSavePomodoroButton.setOnClickListener {
                callback.sendUserChoiceFromStartPomodoroWithoutSavingFragment(false)
                this@StartPomodoroWithoutSavingFragment.dismiss()
            }
        }
    }


}