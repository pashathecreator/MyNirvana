package com.example.mynirvana.domain.sharedPreferences.usecases

data class SharedPreferencesUseCases(
    val checkIsAppRanFirstTimeUseCase: CheckIsAppRanFirstTimeUseCase,
    val changeAppRanFirstTime: ChangeAppRanFirstTime
)