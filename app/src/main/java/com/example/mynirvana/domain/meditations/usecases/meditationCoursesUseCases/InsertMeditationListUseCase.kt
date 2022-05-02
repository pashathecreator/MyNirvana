package com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationCoursesRepository

class InsertMeditationListUseCase(private val repository: MeditationCoursesRepository) {
    suspend operator fun invoke(meditationList: List<Meditation>, id: Int) {
        repository.insertMeditationList(meditationList, id)
    }
}