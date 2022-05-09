package com.example.mynirvana.data.meditations.meditationCourses.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.domain.meditations.model.meditationCourse.typeConverter.MeditationConverters

@Database(entities = [MeditationCourse::class], version = 1)
@TypeConverters(MeditationConverters::class)
abstract class MeditationCourseDatabase : RoomDatabase() {

    abstract fun getMeditationCourseDao(): MeditationCourseDao

    companion object {
        const val DATABASE_NAME = "meditation_courses"
    }
}