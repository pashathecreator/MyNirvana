package com.example.mynirvana.domain.sharedPreferences.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun invoke() = sharedPreferencesRepository.getStringValueByKey(ChangeUserNameUseCase.USER_NAME)
}