package com.skelrath.mynirvana.domain.sharedPreferences.usecases

data class SharedPreferencesUseCases(
    val getUserNameUseCase: GetUserNameUseCase,
    val changeUserNameUseCase: ChangeUserNameUseCase,
)