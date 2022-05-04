package com.example.mynirvana.domain.pomodoro.repository

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import kotlinx.coroutines.flow.Flow

interface PomodoroRepository {
    suspend fun getPomodoros(): Flow<List<Pomodoro>>

    suspend fun getPomodoroById(id: Int): Pomodoro?

    suspend fun insertPomodoro(pomodoro: Pomodoro)

    suspend fun deleteMeditation(pomodoro: Pomodoro)
}