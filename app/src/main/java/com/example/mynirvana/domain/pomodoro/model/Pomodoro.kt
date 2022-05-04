package com.example.mynirvana.domain.pomodoro.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Pomodoro(
    val name: String,
    val workTimeInSeconds: Long,
    val relaxTimeInSeconds: Long,
    val quantityOfCircles: Int,
    val imageResourceId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable