package com.example.mynirvana.presentation.meditationTimerActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mynirvana.databinding.ActivityMeditationTimerBinding
import java.util.*

class MeditationTimerActivity : AppCompatActivity() {

    enum class TimerState {
        Paused, Running
    }

    private lateinit var binding: ActivityMeditationTimerBinding

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Running

    private var secondsRemaining: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.actionButton.setOnClickListener {
            updateAction()

            when (timerState) {
                TimerState.Running -> runTimer()
                TimerState.Paused -> pauseTimer()
            }
        }

    }


    override fun onResume() {
        super.onResume()

        initTimer()
    }

    override fun onPause() {
        super.onPause()

        when (timerState) {
            TimerState.Running -> {
                timer.cancel()
            }
            TimerState.Paused -> {
            }
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)


    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)

        setPreviousTimerLength()

        secondsRemaining = PrefUtil.getSecondsRemaining(this)

        if (timerState == TimerState.Running)
            startTimer()

        updateAction()
        updateCountdownUI()

    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        binding.progressCountdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val stringSeconds = secondsInMinuteUntilFinished.toString()

        binding.meditationTimerTV.text = "$minutesUntilFinished:${
            if (stringSeconds.length == 2) stringSeconds
            else "0" + stringSeconds
        }"

        binding.progressCountdown.progress = (timerLengthSeconds - secondsRemaining).toInt()

    }


    private fun pauseTimer() {

    }

    private fun runTimer() {

    }

    private fun updateAction() {
        timerState = if (timerState == TimerState.Running) TimerState.Paused
        else TimerState.Paused
    }

    private fun startTimer() {
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
    }
}


