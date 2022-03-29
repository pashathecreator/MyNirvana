package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class AddMeditationUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: Meditation) {

        repository.insertMeditationButton(meditationButton = meditationButton)

    }

}