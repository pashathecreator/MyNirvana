package com.example.mynirvana.domain.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.sql.Date


@Entity
data class Task(
    val name: String,
    val timeWhenTaskStarts: Long,
    @ColumnInfo(name = "dateOfTask")
    val dateOfTask: Date,
    var isTaskCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable