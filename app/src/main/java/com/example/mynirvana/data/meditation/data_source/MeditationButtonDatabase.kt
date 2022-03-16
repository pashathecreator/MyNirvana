package com.example.mynirvana.data.meditation.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton

@Database(entities = [MeditationButton::class], version = 1)
abstract class MeditationButtonDatabase : RoomDatabase() {

    abstract val meditationButtonDao: MeditationButtonDao

    companion object {
        const val DATABASE_NAME = "meditation_buttons_db"
    }

}