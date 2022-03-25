package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class GetMeditationUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: Meditation): List<Meditation> {
        return repository.getMeditationButtons()
    }

}

