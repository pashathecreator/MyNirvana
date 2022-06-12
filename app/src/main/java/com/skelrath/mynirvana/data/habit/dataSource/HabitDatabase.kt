package com.skelrath.mynirvana.data.habit.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.task.model.DateConverters


@Database(entities = [Habit::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        const val DATABASE_NAME = "habit"
    }

}