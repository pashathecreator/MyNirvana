package com.example.mynirvana.presentation.dialogs.pomodoroTimerOnFinishDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentPomodoroTimerOnFinishBinding
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity.PomodoroTimerOnFinishCallback

class PomodoroTimerOnFinishFragment : DialogFragment() {

    companion object {
        var isDialogResumed: Boolean = false
    }

    fun provideCallback(callback: PomodoroTimerOnFinishCallback) {
        this.callback = callback
    }

    private lateinit var callback: PomodoroTimerOnFinishCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentPomodoroTimerOnFinishBinding

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
        isDialogResumed = false
        callback.pomodoroTimerOnFinishFragmentOnDismiss()
        super.onDismiss(dialog)
    }
}