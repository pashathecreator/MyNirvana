package com.example.mynirvana.domain.habit.useCases

import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.habit.repository.HabitRepository

class DeleteHabitUseCase(private val habitRepository: HabitRepository) {
    suspend fun invoke(habit: Habit) = habitRepository.deleteHabit(habit)
}