package com.example.mynirvana.domain.meditations.usecases

import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository

class GetMeditationsUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(): List<Meditation> {
        return repository.getMeditations()
    }

}

