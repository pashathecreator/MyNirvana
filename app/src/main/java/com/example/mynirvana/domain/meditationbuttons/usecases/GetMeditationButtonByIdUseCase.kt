package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.MeditationButton
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class GetMeditationButtonByIdUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(id: Int): MeditationButton? {

        return repository.getMeditationButtonById(id = id)

    }

}