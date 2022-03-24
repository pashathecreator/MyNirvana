package com.example.mynirvana.domain.meditationButtons.usecases

data class MeditationButtonUseCases(
    val addMeditationButtonUseCase: AddMeditationButtonUseCase,
    val deleteMeditationButtonUseCase: DeleteMeditationButtonUseCase,
    val getMeditationButtonByIdUseCase: GetMeditationButtonByIdUseCase,
    val getMeditationButtonsUseCase: GetMeditationButtonsUseCase
)