package com.skelrath.library.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_MAX
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.notificationman.library.R
import com.skelrath.library.datastore.AppDataStoreImpl
import com.skelrath.library.extensions.dataStore
import java.util.*

class LocalNotificationShowWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "LNShowWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val notificationID = System.currentTimeMillis().toInt()
            val classPath = inputData.getString(LocalNotificationPostWorker.CLASS_PATH_KEY)
            val title = inputData.getString(LocalNotificationPostWorker.TITLE_KEY)
            val desc = inputData.getString(LocalNotificationPostWorker.DESC_KEY)
            val smallIconResourceId =
                inputData.getInt(LocalNotificationPostWorker.SMALL_ICON_RESOURCE_ID_KEY, 0)
            val largeIconResourceId =
                inputData.getInt(LocalNotificationPostWorker.LARGE_ICON_RESOURCE_ID_KEY, 0)
            val notification = createNotification(
                context = context,
                classPath = classPath!!,
                title = title,
                body = desc,
                smallIconResourceId = smallIconResourceId,
                largeIconResourceId = largeIconResourceId,
                notificationID = notificationID,
            )
            showNotification(
                context = context,
                notification = notification,
                notificationID = notificationID
            )
            deleteWorkerId(
                context = context,
                id = workerParams.id
            )

            Log.d(TAG, "local notification showed")
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "local notification show worker has failed: ${e.message}")
            Result.failure()
        }
    }

    private fun createNotification(
        context: Context,
        classPath: String,
        title: String?,
        body: String?,
        smallIconResourceId: Int,
        largeIconResourceId: Int,
        notificationID: Int
    ): Notification {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            component = ComponentName(context.packageName, classPath)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationID,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            else
                PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat
            .Builder(context, channelId)
            .setSmallIcon(smallIconResourceId)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_SOUND)
            .setColorized(true)
            .setContentTitle(
                if (title.isNullOrBlank().not())
                    title
                else
                    context.getString(R.string.app_name)
            )
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0))
            .setPriority(IMPORTANCE_MAX)

        return if (smallIconResourceId == 0) {
            notificationBuilder
                .setStyle(getBigTextNotificationStyle(body))
                .build()
        } else {
            val roundedBitmap = BitmapFactory.decodeResource(context.resources, largeIconResourceId)
            val style = getBigTextNotificationStyle(body)
            notificationBuilder
                .setLargeIcon(roundedBitmap)
                .setStyle(style)
                .build()
        }
    }
}

private fun getBigTextNotificationStyle(
    body: String?
) = NotificationCompat
    .BigTextStyle()
    .bigText(body)


private fun showNotification(
    context: Context,
    notification: Notification,
    notificationID: Int
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = context.getString(R.string.default_notification_channel_id)
    val channelName = context.getString(R.string.default_notification_channel_name)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(notificationID, notification)
}

private suspend fun deleteWorkerId(context: Context, id: UUID) {
    AppDataStoreImpl(context.dataStore)
        .deleteWorkerId(id = id)
}
