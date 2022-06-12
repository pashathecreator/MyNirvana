package com.skelrath.mynirvana.data.meditations.meditation.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation

@Database(entities = [Meditation::class], version = 1)
abstract class MeditationDatabase : RoomDatabase() {

    abstract fun getMeditationDao(): MeditationDao

    companion object {
        const val DATABASE_NAME = "meditation"
    }

}