package com.example.mynirvana.domain.meditationbuttons.usecases

import com.example.mynirvana.domain.meditationButtons.usecases.AddMeditationUseCase
import com.example.mynirvana.domain.meditationButtons.usecases.DeleteMeditationUseCase
import com.example.mynirvana.domain.meditationButtons.usecases.GetMeditationByIdUseCase

data class MeditationUseCases(
    val addMeditationUseCase: AddMeditationUseCase,
    val deleteMeditationUseCase: DeleteMeditationUseCase,
    val getMeditationByIdUseCase: GetMeditationByIdUseCase,
    val getMeditationsUseCase: GetMeditationsUseCase
)