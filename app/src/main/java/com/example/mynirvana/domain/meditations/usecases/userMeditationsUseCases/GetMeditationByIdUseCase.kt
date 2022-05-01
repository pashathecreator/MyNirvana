package com.example.mynirvana.domain.meditations.usecases.userMeditationsUseCases

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository

class GetMeditationByIdUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(id: Int): Meditation? {

        return repository.getMeditationById(id = id)

    }

}