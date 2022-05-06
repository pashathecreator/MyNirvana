package com.example.mynirvana.domain.timer

import android.app.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class Timer : Service() {
    protected var _secondsRemaining: MutableSharedFlow<Long> = MutableSharedFlow(0)
    val secondsRemaining: Flow<Long>
        get() = _secondsRemaining

    protected var millisRemainingForTimer: Long = 0

    abstract fun startTimer(totalTimeInSeconds: Long = millisRemainingForTimer)
    abstract suspend fun updateTimeRemaining(newValueOfSeconds: Long)
    abstract fun pauseTimer(isCanceled: Boolean = false)
}