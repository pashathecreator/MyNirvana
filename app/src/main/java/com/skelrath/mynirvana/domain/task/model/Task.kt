package com.skelrath.mynirvana.domain.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skelrath.mynirvana.domain.notification.NotificationIdCreator
import java.io.Serializable
import java.sql.Date
import java.util.*


@Entity
data class Task(
    val name: String,
    val timeWhenTaskStarts: Long,
    @ColumnInfo(name = "dateOfTask")
    val dateOfTask: Date,
    var isTaskCompleted: Boolean = false,
    val notificationDate: Date? = null,
    val notificationTimeInSeconds: Long? = null,
    val notificationId: Int = UUID.randomUUID().hashCode(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable