package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.MeditationButton
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class DeleteMeditationButtonUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: MeditationButton) {

        repository.deleteMeditationButton(meditationButton = meditationButton)

    }

}