package com.skelrath.mynirvana.domain.pomodoro.useCases

import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.repository.PomodoroRepository

class AddPomodoroUseCase(private val repository: PomodoroRepository) {
    suspend fun invoke(pomodoro: Pomodoro) = repository.insertPomodoro(pomodoro)
}