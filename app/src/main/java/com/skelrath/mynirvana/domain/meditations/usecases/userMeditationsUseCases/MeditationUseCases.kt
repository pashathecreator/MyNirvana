package com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases


data class MeditationUseCases(
    val addMeditationUseCase: AddMeditationUseCase,
    val deleteMeditationUseCase: DeleteMeditationUseCase,
    val getMeditationByIdUseCase: GetMeditationByIdUseCase,
    val getMeditationsUseCase: GetMeditationsUseCase
)