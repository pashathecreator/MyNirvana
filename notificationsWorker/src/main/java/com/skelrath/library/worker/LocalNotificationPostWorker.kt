package com.skelrath.library.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.skelrath.library.NotificationIds
import com.skelrath.library.datastore.AppDataStoreImpl
import com.skelrath.library.extensions.dataStore
import java.util.*
import java.util.concurrent.TimeUnit

class LocalNotificationPostWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "LNPostWorker"

        const val DEFAULT_TIME_INTERVAL = 5L // 5 secs

        const val NOTIFICATION_ID_PATH_KEY = "notification_path_key"
        const val CLASS_PATH_KEY = "class_name_key"
        const val TITLE_KEY = "title_key"
        const val DESC_KEY = "desc_key"
        const val SMALL_ICON_RESOURCE_ID_KEY = "small_icon_resource_id_key"
        const val LARGE_ICON_RESOURCE_ID_KEY = "large_icon_resource_id_key"
        const val TIME_INTERVAL_KEY = "time_interval_key"
    }

    override suspend fun doWork(): Result {
        return try {
            enqueueNotification()
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "local notification post worker has failed: ${e.message}")
            Result.failure()
        }
    }

    private suspend fun enqueueNotification() {
        val notificationId = inputData.getInt(NOTIFICATION_ID_PATH_KEY, 0)
        val classPath = inputData.getString(CLASS_PATH_KEY)
        val title = inputData.getString(TITLE_KEY)
        val desc = inputData.getString(DESC_KEY)
        val smallIconResourceID = inputData.getInt(SMALL_ICON_RESOURCE_ID_KEY, 0)
        val largeIconResourceID = inputData.getInt(LARGE_ICON_RESOURCE_ID_KEY, 0)
        val timeInternal = inputData.getLong(TIME_INTERVAL_KEY, DEFAULT_TIME_INTERVAL)
        val data = Data.Builder().apply {
            putString(CLASS_PATH_KEY, classPath)
            putString(TITLE_KEY, title)
            putString(DESC_KEY, desc)
            putInt(SMALL_ICON_RESOURCE_ID_KEY, smallIconResourceID)
            putInt(LARGE_ICON_RESOURCE_ID_KEY, largeIconResourceID)
        }
        val localNotificationShowWorkRequest =
            OneTimeWorkRequestBuilder<LocalNotificationShowWorker>()
                .setInitialDelay(timeInternal, TimeUnit.SECONDS)
                .setInputData(data.build())
                .build()
        saveWorkerId(notificationId, localNotificationShowWorkRequest.id)
        WorkManager.getInstance(context).enqueue(localNotificationShowWorkRequest)
    }

    private suspend fun saveWorkerId(id: Int, uuid: UUID) {
        NotificationIds.setUuidById(id, uuid)
        AppDataStoreImpl(context.dataStore)
            .saveWorkerId(id = uuid)
    }
}