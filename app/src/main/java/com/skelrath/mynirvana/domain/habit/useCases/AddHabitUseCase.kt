package com.skelrath.mynirvana.domain.habit.useCases

import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.repository.HabitRepository

class AddHabitUseCase(private val habitRepository: HabitRepository) {
    suspend fun invoke(habit: Habit) = habitRepository.insertHabit(habit)
}