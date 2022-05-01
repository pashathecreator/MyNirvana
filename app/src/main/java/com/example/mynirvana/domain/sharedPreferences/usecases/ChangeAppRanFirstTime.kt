package com.example.mynirvana.domain.sharedPreferences.usecases

import com.example.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository

class ChangeAppRanFirstTime(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun changeAppRanFirstTime(newValue: Boolean) =
        sharedPreferencesRepository.updateSharedPreferencesValueByKey(
            IS_APP_RAN_FIRST_TIME, newValue
        )

    private companion object {
        const val IS_APP_RAN_FIRST_TIME = "isAppRanFirstTime"
    }
}