package com.skelrath.mynirvana.data.task.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skelrath.mynirvana.domain.task.model.DateConverters
import com.skelrath.mynirvana.domain.task.model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task"
    }

}