package com.example.mynirvana.presentation.meditationTimerActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.core.view.doOnAttach
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationTimerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MeditationTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationTimerBinding
    private val viewModel: MeditationTimerViewModel by viewModels()

    private var secondsRemainingInString: String = ""
    private var totalTimeInSeconds: Long = 0
    private var currentSecondsRemaining: Long = 0

    private var currentAction: TimerState = TimerState.Playing


    enum class TimerState {
        Paused, Playing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        totalTimeInSeconds = intent.getSerializableExtra("TIME IN SECONDS") as Long
        viewModel.startTimer(totalTimeInSeconds)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.actionButton.setOnClickListener {
            currentAction =
                if (currentAction == TimerState.Paused) TimerState.Playing else TimerState.Paused

            when (currentAction) {
                TimerState.Paused -> pauseCountDownTimer()
                TimerState.Playing -> playCountDownTimer()
            }

        }

    }

    private fun pauseCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_pause_icon)
        viewModel.pauseTimer()
    }

    private fun playCountDownTimer() {
        binding.actionButton.setImageResource(R.drawable.ic_play_icon)
        viewModel.startTimer(totalTimeInSeconds)
    }

    private fun updateCountDownTimerUI() {
        binding.timeTV.text = secondsRemainingInString
        binding.progressCountdown.progress = (totalTimeInSeconds - currentSecondsRemaining).toInt()
    }

    private fun initObserver() {
        viewModel.remainingTime.observe(this) {
            this.secondsRemainingInString = secondsInLongToStringFormat(it)
            currentSecondsRemaining = it
            updateCountDownTimerUI()
        }
    }

    private fun secondsInLongToStringFormat(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes

        return "$minutes:$tempSeconds"
    }

}


