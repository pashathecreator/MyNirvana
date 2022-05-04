package com.example.mynirvana.data.pomodoro.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynirvana.domain.pomodoro.model.Pomodoro


@Database(entities = [Pomodoro::class], version = 1)
abstract class PomodoroDatabase : RoomDatabase() {

    abstract fun getPomodoroDao(): PomodoroDao

    companion object {
        const val DATABASE_NAME = "pomodoro"
    }
}