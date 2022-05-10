package com.example.mynirvana.data.meditations.meditationCourses.dataSource

import androidx.room.*
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationCourseDao {
    @Query("SELECT * from meditation_courses")
    fun getMeditationCourses(): Flow<List<MeditationCourse>>

    @Query("SELECT * FROM meditation_courses WHERE meditation_course_id = :id")
    suspend fun getMeditationCourseById(id: Int): MeditationCourse?

    @Query(
        "UPDATE meditation_courses SET meditation_course_meditations = :meditations WHERE meditation_course_id = :id"
    )
    suspend fun insertMeditationList(meditations: List<Meditation>, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditationCourse(meditationCourse: MeditationCourse)

    @Delete
    suspend fun deleteMeditationCourse(meditationCourse: MeditationCourse)
}