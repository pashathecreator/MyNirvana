package com.skelrath.mynirvana.domain.sharedPreferences.usecases

import com.skelrath.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository

class GetUserNameUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun invoke() = sharedPreferencesRepository.getStringValueByKey(ChangeUserNameUseCase.USER_NAME)
}