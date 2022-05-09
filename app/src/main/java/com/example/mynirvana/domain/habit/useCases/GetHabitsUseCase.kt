package com.example.mynirvana.domain.habit.useCases

import com.example.mynirvana.domain.habit.repository.HabitRepository

class GetHabitsUseCase(private val habitRepository: HabitRepository) {
    fun invoke() = habitRepository.getHabits()
}