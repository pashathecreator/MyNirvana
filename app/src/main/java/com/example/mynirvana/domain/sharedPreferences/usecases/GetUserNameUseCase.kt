package com.example.mynirvana.domain.sharedPreferences.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynirvana.domain.sharedPreferences.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    private val userNameMutableLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
        get() = userNameMutableLiveData

    fun invoke() {
        userNameMutableLiveData.value =
            sharedPreferencesRepository.getStringValueByKey(ChangeUserNameUseCase.USER_NAME)
    }
}