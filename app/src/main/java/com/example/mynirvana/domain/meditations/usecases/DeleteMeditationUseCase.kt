package com.example.mynirvana.domain.meditations.usecases

import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository

class DeleteMeditationUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(meditationButton: Meditation) {

        repository.deleteMeditation(meditationButton = meditationButton)

    }

}