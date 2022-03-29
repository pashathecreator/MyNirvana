package com.example.mynirvana.domain.meditationbuttons.usecases

import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class GetMeditationsUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(): List<Meditation> {
        return repository.getMeditations()
    }

}

