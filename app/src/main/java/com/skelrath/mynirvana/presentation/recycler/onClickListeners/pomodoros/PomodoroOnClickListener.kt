package com.skelrath.mynirvana.presentation.recycler.onClickListeners.pomodoros

import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro

interface PomodoroOnClickListener {
    fun onPomodoroStart(pomodoro: Pomodoro)
    fun onPomodoroDelete(pomodoro: Pomodoro)
}