package com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases

data class MeditationCoursesUseCases(
    val getMeditationCoursesUseCase: GetMeditationCoursesUseCase,
    val createMeditationCoursesUseCase: CreateMeditationCoursesUseCase,
    val insertMeditationListUseCase: InsertMeditationListUseCase
)