package com.example.mynirvana.presentation.meditationOnFinishFragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.databinding.FragmentMeditationOnFinishBinding
import com.example.mynirvana.presentation.meditationTimerActivity.MeditationOnFinishFragmentCallback

class MeditationOnFinishFragment : DialogFragment() {
    private lateinit var binding: FragmentMeditationOnFinishBinding
    private lateinit var callback: MeditationOnFinishFragmentCallback
    private var time: Long = 0L
    private var isDissmisedBecauseOfButtonClicked = false

    fun provideCallback(callback: MeditationOnFinishFragmentCallback) {
        this.callback = callback
    }

    fun provideTimeForMeditation(time: Long) {
        this.time = time
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeditationOnFinishBinding.inflate(inflater)

        binding.backToHomeFragmentInMeditationOnFinish.setOnClickListener {
            callback.meditationOnFinishUserChoice(true)
            isDissmisedBecauseOfButtonClicked = true
            this.dismiss()
        }

        binding.startMeditationOneMoreTime.setOnClickListener {
            callback.meditationOnFinishUserChoice(false)
            isDissmisedBecauseOfButtonClicked = true
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
        if (isDissmisedBecauseOfButtonClicked) {
            callback.meditationOnFinishFragmentDestroyed()
            super.onDismiss(dialog)
        }
    }

}