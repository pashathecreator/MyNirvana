package com.example.mynirvana.data.task.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynirvana.domain.meditations.model.meditationCourse.typeConverter.MeditationConverters
import com.example.mynirvana.domain.task.model.DateConverters
import com.example.mynirvana.domain.task.model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task"
    }

}