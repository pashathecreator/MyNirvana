package com.example.mynirvana.data.meditationCourses.dataSource

import androidx.room.*
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationCourseDao {
    @Query("SELECT * from meditation_courses")
    fun getMeditationCourses(): Flow<List<MeditationCourse>>

    @Query("SELECT * FROM meditation_courses WHERE id = :id")
    suspend fun getMeditationCourseById(id: Int): MeditationCourse?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditationCourse(meditationCourse: MeditationCourse)

    @Delete
    suspend fun deleteMeditationCourse(meditationCourse: MeditationCourse)
}