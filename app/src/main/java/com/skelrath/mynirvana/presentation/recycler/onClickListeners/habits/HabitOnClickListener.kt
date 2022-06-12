package com.skelrath.mynirvana.presentation.recycler.onClickListeners.habits

import com.skelrath.mynirvana.domain.habit.model.Habit

interface HabitOnClickListener {
    fun onHabitComplete(habit: Habit)
    fun onHabitRemoved(habit: Habit)
}