package com.example.mynirvana.domain.timer

import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import kotlinx.coroutines.*

class TimerService : Timer() {

    private lateinit var timer: CountDownTimer
    private var isTimerPaused: Boolean = false

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun startTimer(totalTimeInSeconds: Long) {
        if (!isTimerPaused)
            millisRemainingForTimer = totalTimeInSeconds * 1000

        timer = object : CountDownTimer(millisRemainingForTimer, 1000) {
            override fun onTick(newValueOfMillis: Long) {

                CoroutineScope(Dispatchers.IO).launch {
                    updateTimeRemaining(millisToSeconds(millisRemainingForTimer))
                }
                millisRemainingForTimer = newValueOfMillis


            }

            override fun onFinish() {
//                stopTimer()
            }

        }.start()

    }

    private fun millisToSeconds(millis: Long): Long {
        return millis / 1000
    }

    override suspend fun updateTimeRemaining(newValueOfSeconds: Long) {
        _secondsRemaining.emit(newValueOfSeconds)
    }

    override fun pauseTimer() {
        timer.cancel()
        isTimerPaused = true
    }

    override fun stopTimer() {
//        timer.onFinish()
    }


}