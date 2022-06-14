package com.skelrath.mynirvana.domain.habit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import java.io.Serializable
import java.sql.Date

@Entity
data class Habit(
    val name: String? = "",
    var isHabitCompleted: Boolean? = false,
    var habitDate: Date? = null,
    val notificationTimeInSeconds: Long? = null,
    val fireBaseId: Int? = RandomIdCreator.createId(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable