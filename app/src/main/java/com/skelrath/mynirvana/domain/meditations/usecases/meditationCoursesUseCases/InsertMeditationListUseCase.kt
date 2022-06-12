package com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.repository.MeditationCoursesRepository

class InsertMeditationListUseCase(private val repository: MeditationCoursesRepository) {
    suspend operator fun invoke(meditationList: List<Meditation>, id: Int) {
        repository.insertMeditationList(meditationList, id)
    }
}