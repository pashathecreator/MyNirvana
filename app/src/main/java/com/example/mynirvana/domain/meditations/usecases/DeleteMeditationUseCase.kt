package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class DeleteMeditationUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: Meditation) {

        repository.deleteMeditationButton(meditationButton = meditationButton)

    }

}