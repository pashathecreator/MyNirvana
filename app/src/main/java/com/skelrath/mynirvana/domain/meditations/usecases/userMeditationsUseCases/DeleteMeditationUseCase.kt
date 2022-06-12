package com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases

import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.repository.MeditationRepository

class DeleteMeditationUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(meditationButton: Meditation) {

        repository.deleteMeditation(meditationButton = meditationButton)

    }

}