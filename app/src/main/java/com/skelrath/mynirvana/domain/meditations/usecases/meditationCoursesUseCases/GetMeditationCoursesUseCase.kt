package com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.skelrath.mynirvana.domain.meditations.repository.MeditationCoursesRepository
import kotlinx.coroutines.flow.Flow

class GetMeditationCoursesUseCase(private val meditationCoursesRepository: MeditationCoursesRepository) {
    suspend fun invoke(): Flow<List<MeditationCourse>> =
        meditationCoursesRepository.getMeditationCourses()
}