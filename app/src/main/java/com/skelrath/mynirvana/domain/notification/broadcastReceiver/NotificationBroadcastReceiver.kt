package com.skelrath.mynirvana.domain.notification.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.skelrath.library.NotificationWorker
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.notification.CreateNotificationForHabit


class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        (intent.getSerializableExtra(CreateNotificationForHabit.HABIT_EXTRA) as Habit?)?.let {
            showNotification(
                context,
                it
            )
        }
    }

    private fun showNotification(context: Context, habit: Habit) {
        val classPath = "com.skelrath.mynirvana.presentation.activities.mainActivity.MainActivity"
        val title = "Трекер привычки"
        val description =
            "Напоминание о привычке \"${habit.name}\""

        NotificationWorker.Builder(
            context,
            habit.notificationId,
            classPath
        ).setTitle(title).setDescription(description).setTimeInterval(0)
            .setSmallIcon(R.mipmap.ic_launcher_foreground).setLargeIcon(R.mipmap.ic_launcher)
            .schedule()
    }
}