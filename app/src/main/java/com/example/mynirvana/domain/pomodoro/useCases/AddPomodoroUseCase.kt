package com.example.mynirvana.domain.pomodoro.useCases

import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.repository.PomodoroRepository

class AddPomodoroUseCase(private val repository: PomodoroRepository) {
    suspend fun invoke(pomodoro: Pomodoro) = repository.insertPomodoro(pomodoro)
}