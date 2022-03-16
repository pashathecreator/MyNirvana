package com.example.mynirvana.domain.meditationbuttons.usecases

import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.repository.MeditationButtonRepository

class DeleteMeditationButtonUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: MeditationButton) {

        repository.deleteMeditationButton(meditationButton = meditationButton)

    }

}