package com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository

class AddMeditationUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(meditationButton: Meditation) {

        repository.insertMeditation(meditationButton)

    }

}