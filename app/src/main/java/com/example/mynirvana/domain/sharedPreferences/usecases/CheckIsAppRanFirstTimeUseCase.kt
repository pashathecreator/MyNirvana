package com.example.mynirvana.domain.sharedPreferences.usecases

import com.example.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository

class CheckIsAppRanFirstTimeUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun checkIsAppRanFirstTime() = sharedPreferencesRepository.getBooleanValueByKey(
        IS_APP_RAN_FIRST_TIME
    )

    private companion object {
        const val IS_APP_RAN_FIRST_TIME = "isAppRanFirstTime"
    }
}