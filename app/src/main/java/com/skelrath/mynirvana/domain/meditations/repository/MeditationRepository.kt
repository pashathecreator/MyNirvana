package com.skelrath.mynirvana.domain.meditations.repository

import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import kotlinx.coroutines.flow.Flow

interface MeditationRepository {

    suspend fun getMeditations(): Flow<List<Meditation>>

    suspend fun getMeditationById(id: Int): Meditation?

    suspend fun insertMeditation(meditationButton: Meditation)

    suspend fun deleteMeditation(meditationButton: Meditation)


}