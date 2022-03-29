package com.example.mynirvana.domain.meditationButtons.repository

import com.example.mynirvana.domain.meditationButtons.model.Meditation

interface MeditationButtonRepository {

    suspend fun getMeditations(): List<Meditation>

    suspend fun getMeditationButtonById(id: Int): Meditation?

    suspend fun insertMeditationButton(meditationButton: Meditation)

    suspend fun deleteMeditationButton(meditationButton: Meditation)


}