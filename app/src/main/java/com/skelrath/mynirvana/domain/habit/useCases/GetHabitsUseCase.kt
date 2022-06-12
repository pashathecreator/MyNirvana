package com.skelrath.mynirvana.domain.habit.useCases

import com.skelrath.mynirvana.domain.habit.repository.HabitRepository

class GetHabitsUseCase(private val habitRepository: HabitRepository) {
    fun invoke() = habitRepository.getHabits()
}