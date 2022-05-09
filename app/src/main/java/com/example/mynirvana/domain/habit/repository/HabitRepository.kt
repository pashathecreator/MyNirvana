package com.example.mynirvana.domain.habit.repository

import com.example.mynirvana.domain.habit.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getHabits(): Flow<List<Habit>>

    suspend fun insertHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

}