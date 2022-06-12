package com.skelrath.mynirvana.domain.habit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date
import java.util.*

@Entity
data class Habit(
    val name: String,
    var isHabitCompleted: Boolean = false,
    var habitDate: Date,
    val notificationTimeInSeconds: Long? = null,
    val notificationId: Int = UUID.randomUUID().hashCode(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable