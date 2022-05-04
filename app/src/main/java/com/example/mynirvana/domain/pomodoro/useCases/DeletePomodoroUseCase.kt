package com.example.mynirvana.domain.pomodoro.useCases

import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.repository.PomodoroRepository

class DeletePomodoroUseCase(private val repository: PomodoroRepository) {
    suspend fun invoke(pomodoro: Pomodoro) = repository.deleteMeditation(pomodoro)
}