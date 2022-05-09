package com.example.mynirvana.data.habit.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynirvana.domain.habit.model.Habit


@Database(entities = [Habit::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        const val DATABASE_NAME = "habit"
    }

}