package com.example.mynirvana.domain.timer

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TimerService : Timer() {

    private lateinit var timer: CountDownTimer
    private var isTimerPaused: Boolean = false

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun startTimer(totalTimeInSeconds: Long) {
        if (!isTimerPaused)
            secondsRemainingForTimer = totalTimeInSeconds

        timer = object : CountDownTimer(secondsRemainingForTimer, 1000) {
            override fun onTick(newValueOfSeconds: Long) {

                CoroutineScope(Dispatchers.IO).launch {
                    updateTimeRemaining(newValueOfSeconds)
                }
                secondsRemainingForTimer = newValueOfSeconds


            }

            override fun onFinish() {
//                stopTimer()
            }

        }.start()

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