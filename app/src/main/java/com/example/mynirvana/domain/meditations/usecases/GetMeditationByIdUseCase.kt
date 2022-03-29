package com.example.mynirvana.domain.meditations.usecases

import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository

class GetMeditationByIdUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(id: Int): Meditation? {

        return repository.getMeditationById(id = id)

    }

}