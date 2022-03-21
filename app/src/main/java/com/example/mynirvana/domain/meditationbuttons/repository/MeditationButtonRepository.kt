package com.example.mynirvana.domain.meditationbuttons.repository

import androidx.lifecycle.LiveData
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton
import kotlinx.coroutines.flow.Flow

interface MeditationButtonRepository {

    suspend fun getMeditationButtons(): List<MeditationButton>

    suspend fun getMeditationButtonById(id: Int): MeditationButton?

    suspend fun insertMeditationButton(meditationButton: MeditationButton)

    suspend fun deleteMeditationButton(meditationButton: MeditationButton)


}