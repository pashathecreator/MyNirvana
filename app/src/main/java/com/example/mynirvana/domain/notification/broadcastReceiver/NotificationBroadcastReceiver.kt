package com.example.mynirvana.domain.notification.broadcastReceiver

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mynirvana.R


class NotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val channelID = "channel1"
        const val notificationExtra = "notificationExtra"
        const val notificationIdExtra = "notificationIdExtra"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
    }

    private var title = ""
    private var message = ""
    private var notificationID = 0


    override fun onReceive(context: Context, intent: Intent) {
        val notification = intent.getParcelableExtra("NOTIFICATION") as Notification?

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationID, notification)
    }

//    private fun deserializeTitleAndMessageFromIntent(intent: Intent) {
//        title = intent.getStringExtra(titleExtra) as String
//        message = intent.getStringExtra(messageExtra) as String
//        notificationID = intent.getIntExtra(notificationIdExtra, 0)
//    }


}