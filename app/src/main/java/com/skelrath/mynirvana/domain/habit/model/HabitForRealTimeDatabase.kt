package com.skelrath.mynirvana.domain.habit.model

import com.skelrath.mynirvana.domain.notification.RandomIdCreator
import java.sql.Date
import java.util.*

data class HabitForRealTimeDatabase(
    val name: String? = "",
    var isHabitCompleted: Boolean? = false,
    var habitDate: Long? = Calendar.getInstance().time.time,
    val notificationTimeInSeconds: Long? = null,
    val fireBaseId: Int? = null
) {
    companion object {
        fun convertHabitForRealTimeDatabaseIntoHabit(habitForRealTimeDatabase: HabitForRealTimeDatabase) =
            Habit(
                name = habitForRealTimeDatabase.name,
                habitDate = Date(habitForRealTimeDatabase.habitDate!!),
                notificationTimeInSeconds = habitForRealTimeDatabase.notificationTimeInSeconds,
                fireBaseId = habitForRealTimeDatabase.fireBaseId
            )

        fun convertHabitIntoHabitForRealTimeDatabase(habit: Habit) =
            HabitForRealTimeDatabase(
                name = habit.name,
                habitDate = habit.habitDate?.time,
                notificationTimeInSeconds = habit.notificationTimeInSeconds,
                fireBaseId = habit.fireBaseId
            )
    }
}