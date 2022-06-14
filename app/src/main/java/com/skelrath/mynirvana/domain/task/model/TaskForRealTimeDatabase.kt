package com.skelrath.mynirvana.domain.task.model

import java.sql.Date
import java.util.*

data class TaskForRealTimeDatabase(
    val name: String? = "",
    val timeWhenTaskStarts: Long? = 46800,
    val dateOfTask: Long? = Calendar.getInstance().time.time,
    val notificationDate: Long? = null,
    val notificationTimeInSeconds: Long? = null,
    val fireBaseId: Int? = null,
) {
    companion object {
        fun convertTaskForRealTimeDatabaseIntoTask(taskForRealTimeDatabase: TaskForRealTimeDatabase) =
            Task(
                name = taskForRealTimeDatabase.name,
                timeWhenTaskStarts = taskForRealTimeDatabase.timeWhenTaskStarts,
                dateOfTask = Date(taskForRealTimeDatabase.dateOfTask!!),
                notificationDate = taskForRealTimeDatabase.notificationDate?.let { Date(it) },
                fireBaseId = taskForRealTimeDatabase.fireBaseId
            )

        fun convertTaskIntoTaskForRealTimeDatabase(task: Task) =
            TaskForRealTimeDatabase(
                name = task.name, timeWhenTaskStarts = task.timeWhenTaskStarts,
                dateOfTask = task.dateOfTask!!.time,
                notificationDate = task.notificationDate?.time,
                notificationTimeInSeconds = task.notificationTimeInSeconds,
                fireBaseId = task.fireBaseId
            )
    }
}