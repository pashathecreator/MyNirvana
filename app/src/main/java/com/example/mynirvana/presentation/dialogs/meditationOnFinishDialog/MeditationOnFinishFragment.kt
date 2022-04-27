package com.example.mynirvana.presentation.dialogs.meditationOnFinishDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentMeditationOnFinishBinding
import com.example.mynirvana.presentation.activities.meditationTimerActivity.MeditationOnFinishFragmentCallback

class MeditationOnFinishFragment : DialogFragment() {
    private lateinit var binding: FragmentMeditationOnFinishBinding
    private lateinit var callback: MeditationOnFinishFragmentCallback
    private var time: Long = 0L
    private var isDismissedBecauseOfButtonClicked = false

    fun provideCallback(callback: MeditationOnFinishFragmentCallback) {
        this.callback = callback
    }

    fun provideTimeForMeditation(time: Long) {
        this.time = time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationOnFinishBinding.inflate(inflater)

        binding.backToHomeFragmentInMeditationOnFinish.setOnClickListener {
            callback.meditationOnFinishUserChoice(true)
            isDismissedBecauseOfButtonClicked = true
            this.dismiss()
        }

        binding.startMeditationOneMoreTime.setOnClickListener {
            callback.meditationOnFinishUserChoice(false)
            isDismissedBecauseOfButtonClicked = true
            this.dismiss()
        }

        binding.meditationTimeTV.text = "Вы медитировали ${secondsInLongToStringFormat(time)}!"

        return binding.root
    }

    private fun secondsInLongToStringFormat(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes
        val secondsToString = if (tempSeconds < 10) "0$tempSeconds" else tempSeconds.toString()

        return "$minutes:$secondsToString"
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (isDismissedBecauseOfButtonClicked) {
            callback.meditationOnFinishFragmentDestroyed()
            super.onDismiss(dialog)
        }
    }

}