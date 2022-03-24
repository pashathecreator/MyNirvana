package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.MeditationButton
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class GetMeditationButtonsUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: MeditationButton): List<MeditationButton> {
        return repository.getMeditationButtons()
    }

}

