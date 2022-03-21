package com.example.mynirvana.domain.meditationbuttons.usecases

import androidx.lifecycle.LiveData
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import com.example.mynirvana.domain.meditationbuttons.repository.MeditationButtonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeditationButtonsUseCase(private val repository: MeditationButtonRepository) {

    suspend operator fun invoke(meditationButton: MeditationButton): List<MeditationButton> {
        return repository.getMeditationButtons()
    }

}

