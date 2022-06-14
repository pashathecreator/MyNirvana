package com.skelrath.mynirvana.domain.pomodoro.model

import android.content.Context
import com.skelrath.mynirvana.domain.notification.RandomIdCreator

data class PomodoroForRealTimeDatabase(
    val name: String? = "",
    val workTimeInSeconds: Long? = 600,
    val relaxTimeInSeconds: Long? = 300,
    val quantityOfCircles: Int? = 4,
    val imageResourceId: String? = "com.skelrath.mynirvana:drawable/ic_rectangle_blue",
    val fireBaseId: Int? = RandomIdCreator.createId(),
    val id: Int? = null
) {
    companion object {
        fun convertPomodoroForRealTimeDatabaseIntoPomodoro(
            pomodoroForRealTimeDatabase: PomodoroForRealTimeDatabase,
            context: Context
        ): Pomodoro = Pomodoro(
            name = pomodoroForRealTimeDatabase.name,
            workTimeInSeconds = pomodoroForRealTimeDatabase.workTimeInSeconds,
            relaxTimeInSeconds = pomodoroForRealTimeDatabase.relaxTimeInSeconds,
            quantityOfCircles = pomodoroForRealTimeDatabase.quantityOfCircles,
            imageResourceId = context.resources.getIdentifier(
                pomodoroForRealTimeDatabase.name,
                "id",
                context.packageName
            ),
            isPomodoroCanBeDeleted = true,
            id = pomodoroForRealTimeDatabase.id,
            fireBaseId = pomodoroForRealTimeDatabase.fireBaseId
        )

        fun convertPomodoroIntoPomodoroForRealTimeDatabase(pomodoro: Pomodoro, context: Context) =
            PomodoroForRealTimeDatabase(
                name = pomodoro.name,
                workTimeInSeconds = pomodoro.workTimeInSeconds,
                relaxTimeInSeconds = pomodoro.relaxTimeInSeconds,
                quantityOfCircles = pomodoro.quantityOfCircles,
                imageResourceId = context.resources.getResourceName(pomodoro.imageResourceId!!),
                id = pomodoro.id,
                fireBaseId = pomodoro.fireBaseId
            )

    }
}
