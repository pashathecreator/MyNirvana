package com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.domain.meditations.repository.MeditationCoursesRepository
import kotlinx.coroutines.flow.Flow

class GetMeditationCoursesUseCase(private val meditationCoursesRepository: MeditationCoursesRepository) {
    suspend fun invoke(): Flow<List<MeditationCourse>> =
        meditationCoursesRepository.getMeditationCourses()
}