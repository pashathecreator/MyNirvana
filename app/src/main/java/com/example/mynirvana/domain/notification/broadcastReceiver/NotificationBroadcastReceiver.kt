package com.example.mynirvana.domain.notification.broadcastReceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mynirvana.R
import com.example.mynirvana.domain.notification.model.Notification


class NotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val notificationID = 1
        const val channelID = "channel1"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
    }

    private lateinit var notification: Notification

    override fun onReceive(context: Context, intent: Intent) {
        notification = deserializeNotificationFromIntent(intent)

        val builtNotification =
            NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notification.title).setContentText(notification.message).build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationID, builtNotification)
    }

    private fun deserializeNotificationFromIntent(intent: Intent): Notification {
        with(intent) {
            return getSerializableExtra("NOTIFICATION_INFO") as Notification
        }
    }


}