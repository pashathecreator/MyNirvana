package com.example.mynirvana.presentation.recycler.onClickListeners.habits

import com.example.mynirvana.domain.habit.model.Habit

interface HabitOnClickListener {
    fun onHabitComplete(habit: Habit)
    fun onHabitRemoved(position: Int)
}