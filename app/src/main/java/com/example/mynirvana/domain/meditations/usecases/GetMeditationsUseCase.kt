package com.example.mynirvana.domain.meditations.usecases

import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.domain.meditations.repository.MeditationRepository
import kotlinx.coroutines.flow.Flow

class GetMeditationsUseCase(private val repository: MeditationRepository) {

    suspend operator fun invoke(): Flow<List<Meditation>> {
        return repository.getMeditations()
    }

}

