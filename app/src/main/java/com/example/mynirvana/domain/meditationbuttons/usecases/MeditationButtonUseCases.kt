package com.example.mynirvana.domain.meditationbuttons.usecases

data class MeditationButtonUseCases(
    val addMeditationButtonUseCase: AddMeditationButtonUseCase,
    val deleteMeditationButtonUseCase: DeleteMeditationButtonUseCase,
    val getMeditationButtonByIdUseCase: GetMeditationButtonByIdUseCase,
    val getMeditationButtonsUseCase: GetMeditationButtonsUseCase
)