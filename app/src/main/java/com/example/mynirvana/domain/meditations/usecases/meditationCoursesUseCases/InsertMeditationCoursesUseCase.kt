package com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.domain.meditations.repository.MeditationCoursesRepository

class InsertMeditationCoursesUseCase(private val repository: MeditationCoursesRepository) {
    suspend fun invoke(meditationCourse: MeditationCourse) {
        repository.insertMeditationCourse(meditationCourse)
    }
}