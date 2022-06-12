package com.skelrath.mynirvana.domain.pomodoro.repository

import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import kotlinx.coroutines.flow.Flow

interface PomodoroRepository {
    suspend fun getPomodoros(): Flow<List<Pomodoro>>

    suspend fun getPomodoroById(id: Int): Pomodoro?

    suspend fun insertPomodoro(pomodoro: Pomodoro)

    suspend fun deleteMeditation(pomodoro: Pomodoro)
}