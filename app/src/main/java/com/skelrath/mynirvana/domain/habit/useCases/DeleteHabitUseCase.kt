package com.skelrath.mynirvana.domain.habit.useCases

import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.repository.HabitRepository

class DeleteHabitUseCase(private val habitRepository: HabitRepository) {
    suspend fun invoke(habit: Habit) = habitRepository.deleteHabit(habit)
}