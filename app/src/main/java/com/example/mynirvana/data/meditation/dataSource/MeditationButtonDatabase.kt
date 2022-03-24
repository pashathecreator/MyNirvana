package com.example.mynirvana.data.meditation.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynirvana.domain.meditationButtons.model.MeditationButton

@Database(entities = [MeditationButton::class], version = 1)
abstract class MeditationButtonDatabase : RoomDatabase() {

    abstract fun getMeditationButtonDao() : MeditationButtonDao

    companion object {
        const val DATABASE_NAME = "meditationbutton"
    }

}