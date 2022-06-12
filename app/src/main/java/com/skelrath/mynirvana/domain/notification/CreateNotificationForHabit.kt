package com.skelrath.mynirvana.domain.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.notification.broadcastReceiver.NotificationBroadcastReceiver

object CreateNotificationForHabit {
    const val HABIT_EXTRA = "HABIT_FOR_NOTIFICATION"

    fun createNotification(context: Context, habit: Habit) {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        intent.putExtra(HABIT_EXTRA, habit)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                habit.notificationId, intent,
                FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        habit.notificationTimeInSeconds?.let { time ->
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                (time - 120) * 1000,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    fun deleteNotification(context: Context, habit: Habit) {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                habit.notificationId, intent,
                FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)

    }
}