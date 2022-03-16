package com.example.mynirvana.domain.meditationbuttons.usecases

import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.repository.MeditationButtonRepository

class GetMeditationButtonByIdUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(id: Int): MeditationButton? {

        return repository.getMeditationButtonById(id = id)

    }

}