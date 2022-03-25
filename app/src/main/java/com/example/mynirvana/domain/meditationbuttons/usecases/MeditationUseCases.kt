package com.example.mynirvana.domain.meditationButtons.usecases

data class MeditationUseCases(
    val addMeditationButtonUseCase: AddMeditationUseCase,
    val deleteMeditationButtonUseCase: DeleteMeditationUseCase,
    val getMeditationButtonByIdUseCase: GetMeditationByIdUseCase,
    val getMeditationButtonsUseCase: GetMeditationUseCase
)