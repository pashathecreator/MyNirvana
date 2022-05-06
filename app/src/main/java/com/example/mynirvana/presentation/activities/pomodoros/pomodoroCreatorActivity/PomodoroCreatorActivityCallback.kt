package com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

interface PomodoroCreatorActivityCallback {
    fun sendPickedTime(minutes: Int, seconds: Int)
    fun sendQuantityOfCircles(quantity: Int)
}