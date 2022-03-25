package com.example.mynirvana.domain.meditationButtons.usecases

import com.example.mynirvana.domain.meditationButtons.model.Meditation
import com.example.mynirvana.domain.meditationButtons.repository.MeditationButtonRepository

class GetMeditationByIdUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(id: Int): Meditation? {

        return repository.getMeditationButtonById(id = id)

    }

}