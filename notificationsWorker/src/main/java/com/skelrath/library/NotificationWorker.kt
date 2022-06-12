package com.skelrath.library

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.skelrath.library.datastore.AppDataStoreImpl
import com.skelrath.library.extensions.dataStore
import com.skelrath.library.worker.LocalNotificationPostWorker
import java.util.*

object NotificationWorker {

    private lateinit var appDataStoreImpl: AppDataStoreImpl

    /**
     * @param context A context for on-demand initialization.
     * @param classPathWillBeOpen A string path for on-demand initialization. The activity's
     *                            path that you want to open when the notification is
     *                            clicked. Make sure the class path match with your project
     *                            architecture. Example: "com.notification.man.MainActivity"
     * @param title Title text of the notification. (optional)
     * @param desc Description text of the notification. (optional)
     * @param smallIconResourceID Large and small icon of the notification. (optional)
     * @param largeIconResourceID
     * @param timeInterval Time interval for scheduling the notification. Needs seconds. Default
     *                     value is 5 seconds. (optional)
     */
    data class Builder(
        private val context: Context,
        private val notificationId: Int,
        private val classPathWillBeOpen: String,
        private var title: String? = null,
        private var desc: String? = null,
        private var smallIconResourceID: Int = 0,
        private var largeIconResourceId: Int = 0,
        private var timeInterval: Long? = null
    ) {
        fun setTitle(title: String?) = apply { this.title = title }
        fun setDescription(desc: String?) = apply { this.desc = desc }
        fun setSmallIcon(smallIconResourceId: Int) =
            apply { this.smallIconResourceID = smallIconResourceId }

        fun setLargeIcon(largeIconResourceId: Int) =
            apply { this.largeIconResourceId = largeIconResourceId }

        fun setTimeInterval(timeInterval: Long?) = apply { this.timeInterval = timeInterval }
        fun schedule() {
            appDataStoreImpl = AppDataStoreImpl(context.dataStore)
            scheduleNotification()
        }

        private fun scheduleNotification() {
            val data = Data.Builder().apply {
                putInt(LocalNotificationPostWorker.NOTIFICATION_ID_PATH_KEY, notificationId)
                putString(LocalNotificationPostWorker.CLASS_PATH_KEY, classPathWillBeOpen)
                putString(LocalNotificationPostWorker.TITLE_KEY, title)
                putString(LocalNotificationPostWorker.DESC_KEY, desc)
                putInt(LocalNotificationPostWorker.SMALL_ICON_RESOURCE_ID_KEY, smallIconResourceID)
                putInt(
                    LocalNotificationPostWorker.LARGE_ICON_RESOURCE_ID_KEY,
                    this@Builder.largeIconResourceId
                )
                putLong(
                    LocalNotificationPostWorker.TIME_INTERVAL_KEY,
                    timeInterval ?: LocalNotificationPostWorker.DEFAULT_TIME_INTERVAL
                )
            }
            val localNotificationPostWorkRequest =
                OneTimeWorkRequestBuilder<LocalNotificationPostWorker>()
                    .setInputData(data.build())
                    .build()
            WorkManager.getInstance(context).enqueue(localNotificationPostWorkRequest)
        }
    }

    fun deleteNotificationById(
        context: Context,
        id: Int
    ) {
        val uuid = NotificationIds.getUuidById(id)
        uuid?.let {
            cancelWorkerById(context, it)
        }
        NotificationIds.deleteUuidById(id)
    }


    private fun cancelWorkerById(
        context: Context,
        id: UUID
    ) {
        WorkManager
            .getInstance(context)
            .cancelWorkById(id)
    }
}