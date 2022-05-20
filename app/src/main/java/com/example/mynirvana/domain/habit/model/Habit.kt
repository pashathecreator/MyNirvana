package com.example.mynirvana.domain.habit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

@Entity
data class Habit(
    val name: String,
    var isHabitCompleted: Boolean = false,
    var habitDate: Date,
    val notificationTime: Long? = null,
    val notificationId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)