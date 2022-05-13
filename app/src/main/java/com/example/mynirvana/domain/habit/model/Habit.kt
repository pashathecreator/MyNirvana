package com.example.mynirvana.domain.habit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Habit(
    val name: String,
    var isHabitCompleted: Boolean = false,
    var habitDate: Date,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)