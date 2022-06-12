package com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases

import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.repository.MeditationRepository
import kotlinx.coroutines.flow.Flow

class GetMeditationsUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(): Flow<List<Meditation>> {
        return repository.getMeditations()
    }

}

