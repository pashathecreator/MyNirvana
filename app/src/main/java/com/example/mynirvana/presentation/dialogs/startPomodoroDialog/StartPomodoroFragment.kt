package com.example.mynirvana.presentation.dialogs.startPomodoroDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartPomodoroBinding
import com.example.mynirvana.presentation.mainFragments.productivityFragment.callback.PomodoroTimerStartCallback

class StartPomodoroFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    fun provideCallback(callback: PomodoroTimerStartCallback) {
        this.callback = callback
    }

    fun providePomodoroName(pomodoroName: String) {
        this.pomodoroName = pomodoroName
    }

    private lateinit var callback: PomodoroTimerStartCallback
    private lateinit var binding: FragmentStartPomodoroBinding
    private lateinit var pomodoroName: String

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
                callback.sendUserChoiceFromStartPomodoroDialog(false)
                this@StartPomodoroFragment.dismiss()
            }

            startPomodoroTimerButton.setOnClickListener {
                callback.sendUserChoiceFromStartPomodoroDialog(true)
                this@StartPomodoroFragment.dismiss()
            }

            sureForPomodoroTV.text = "Вы уверены что хотите начать помодоро \"$pomodoroName\""

        }
    }

    override fun onCancel(dialog: DialogInterface) {
        callback.sendUserChoiceFromStartPomodoroDialog(false)
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback.onPomodoroStartDialogDismissed()
        super.onDismiss(dialog)
    }


}