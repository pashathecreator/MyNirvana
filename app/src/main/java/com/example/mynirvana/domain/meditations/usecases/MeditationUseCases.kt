package com.example.mynirvana.domain.meditations.usecases

import com.example.mynirvana.domain.meditations.usecases.AddMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditations.usecases.GetMeditationByIdUseCase
import com.example.mynirvana.domain.meditations.usecases.GetMeditationsUseCase

data class MeditationUseCases(
    val addMeditationUseCase: AddMeditationUseCase,
    val deleteMeditationUseCase: DeleteMeditationUseCase,
    val getMeditationByIdUseCase: GetMeditationByIdUseCase,
    val getMeditationsUseCase: GetMeditationsUseCase
)