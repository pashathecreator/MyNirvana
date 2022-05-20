package com.example.mynirvana.presentation.activities.onBoardingAcitivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val sharedPreferencesUseCases: SharedPreferencesUseCases) :
    ViewModel() {
    fun setUserName(name: String, functionToLaunch: () -> Unit) {
        viewModelScope.launch {
            sharedPreferencesUseCases.changeUserNameUseCase.invoke(name)
            sharedPreferencesUseCases.getUserNameUseCase.invoke()
            functionToLaunch()
        }
    }

}