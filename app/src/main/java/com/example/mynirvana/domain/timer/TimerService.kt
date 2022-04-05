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

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun startTimer(totalTimeInSeconds: Long) {
        timer = object : CountDownTimer(totalTimeInSeconds, 1) {
            override fun onTick(newValueOfSeconds: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    updateTimeRemaining(newValueOfSeconds)
                }

            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }
    }

    override suspend fun updateTimeRemaining(newValueOfSeconds: Long) {
        _secondsRemaining.emit(newValueOfSeconds)
    }


}