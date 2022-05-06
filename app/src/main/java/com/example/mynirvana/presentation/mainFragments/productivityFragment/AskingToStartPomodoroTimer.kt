package com.example.mynirvana.presentation.mainFragments.productivityFragment

import com.example.mynirvana.domain.pomodoro.model.Pomodoro

interface AskingToStartPomodoroTimer {
    fun asksToStartPomodoroTimer(pomodoro: Pomodoro)
    fun onReadyToStartPomodoroTimer()
}