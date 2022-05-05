package com.example.mynirvana.presentation.recycler.onClickListeners.pomodoros

import com.example.mynirvana.domain.pomodoro.model.Pomodoro

interface PomodoroOnClickListener {
    fun onPomodoroStart(pomodoro: Pomodoro)
    fun onPomodoroDelete(pomodoro: Pomodoro)
}