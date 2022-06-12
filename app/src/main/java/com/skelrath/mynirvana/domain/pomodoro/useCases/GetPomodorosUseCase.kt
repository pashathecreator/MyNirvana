package com.skelrath.mynirvana.domain.pomodoro.useCases

import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.repository.PomodoroRepository
import kotlinx.coroutines.flow.Flow

class GetPomodorosUseCase(private val repository: PomodoroRepository) {
    suspend fun invoke(): Flow<List<Pomodoro>> = repository.getPomodoros()

}