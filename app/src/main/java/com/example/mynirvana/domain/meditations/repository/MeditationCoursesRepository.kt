package com.example.mynirvana.domain.meditations.repository

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import kotlinx.coroutines.flow.Flow

interface MeditationCoursesRepository {
    suspend fun getMeditationCourses(): Flow<List<MeditationCourse>>

    suspend fun getMeditationCourseById(id: Int): MeditationCourse?

    suspend fun insertMeditationList(meditations: List<Meditation>, id: Int)

    suspend fun insertMeditationCourse(meditationCourse: MeditationCourse)

    suspend fun deleteMeditationCourse(meditationCourse: MeditationCourse)
}