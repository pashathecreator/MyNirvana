package com.skelrath.mynirvana.domain.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import java.io.Serializable
import java.sql.Date


@Entity
data class Task(
    val name: String? = "",
    val timeWhenTaskStarts: Long? = 46800,
    @ColumnInfo(name = "dateOfTask")
    val dateOfTask: Date? = null,
    var isTaskCompleted: Boolean? = false,
    val notificationDate: Date? = null,
    val notificationTimeInSeconds: Long? = null,
    val fireBaseId: Int? = RandomIdCreator.createId(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable