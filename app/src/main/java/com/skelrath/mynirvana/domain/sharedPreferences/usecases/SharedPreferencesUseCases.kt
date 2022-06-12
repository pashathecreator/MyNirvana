package com.skelrath.mynirvana.domain.sharedPreferences.usecases

data class SharedPreferencesUseCases(
    val checkIsAppRanFirstTimeUseCase: CheckIsAppRanFirstTimeUseCase,
    val getUserNameUseCase: GetUserNameUseCase,
    val changeUserNameUseCase: ChangeUserNameUseCase,
    val changeAppRanFirstTime: ChangeAppRanFirstTime
)