package com.example.mynirvana.presentation.dialogs.savePomodoroAndStartDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSavePomodoroAndStartBinding
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.SavePomodoroAndStartFragmentCallback

class SavePomodoroAndStartFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    fun provideCallback(callback: SavePomodoroAndStartFragmentCallback) {
        this.callback = callback
    }

    private lateinit var binding: FragmentSavePomodoroAndStartBinding
    private lateinit var callback: SavePomodoroAndStartFragmentCallback

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
                callback.sendUserChoiceFromSavePomodoroAndStartFragment(true)
                this@SavePomodoroAndStartFragment.dismiss()
            }

            backToProductivityScreenButton.setOnClickListener {
                callback.sendUserChoiceFromSavePomodoroAndStartFragment(false)
                this@SavePomodoroAndStartFragment.dismiss()
            }
        }
    }


}