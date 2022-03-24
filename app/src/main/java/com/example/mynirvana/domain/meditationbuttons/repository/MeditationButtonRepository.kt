package com.example.mynirvana.domain.meditationButtons.repository

import com.example.mynirvana.domain.meditationButtons.model.MeditationButton

interface MeditationButtonRepository {

    suspend fun getMeditationButtons(): List<MeditationButton>

    suspend fun getMeditationButtonById(id: Int): MeditationButton?

    suspend fun insertMeditationButton(meditationButton: MeditationButton)

    suspend fun deleteMeditationButton(meditationButton: MeditationButton)


}