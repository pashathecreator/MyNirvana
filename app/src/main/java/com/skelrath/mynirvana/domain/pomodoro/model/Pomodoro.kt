package com.skelrath.mynirvana.domain.pomodoro.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import java.io.Serializable


@Entity
data class Pomodoro(
    val name: String? = "",
    val workTimeInSeconds: Long? = 600,
    val relaxTimeInSeconds: Long? = 300 ,
    val quantityOfCircles: Int? = 4,
    val imageResourceId: Int?,
    val isPomodoroCanBeDeleted: Boolean? = false,
    val fireBaseId: Int? = RandomIdCreator.createId(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable


