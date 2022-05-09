package com.example.mynirvana.domain.habit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    val name: String,
    var isCaseCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)