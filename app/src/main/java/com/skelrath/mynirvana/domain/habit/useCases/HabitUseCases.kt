package com.skelrath.mynirvana.domain.habit.useCases

data class HabitUseCases(
    val addHabitUseCase: AddHabitUseCase,
    val deleteHabitUseCase: DeleteHabitUseCase,
    val getHabitsUseCase: GetHabitsUseCase
)