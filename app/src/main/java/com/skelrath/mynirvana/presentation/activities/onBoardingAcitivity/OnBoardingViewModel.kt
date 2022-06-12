package com.skelrath.mynirvana.presentation.activities.onBoardingAcitivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.skelrath.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val sharedPreferencesUseCases: SharedPreferencesUseCases,
    private val meditationCoursesUseCases: MeditationCoursesUseCases
) :
    ViewModel() {

    fun setUserName(name: String, functionToLaunch: () -> Unit) {
        viewModelScope.launch {
            sharedPreferencesUseCases.changeUserNameUseCase.invoke(name)
            functionToLaunch()
        }
    }

    var isAppRanFirstTime =
        sharedPreferencesUseCases.checkIsAppRanFirstTimeUseCase.checkIsAppRanFirstTime()

    init {
        checkIsUserNameIsEmpty()?.let {
            isAppRanFirstTime = it
        }
    }

    private fun checkIsUserNameIsEmpty() =
        sharedPreferencesUseCases.getUserNameUseCase.invoke()?.isEmpty()


    fun setFalseToAppRanFirstTime() {
        sharedPreferencesUseCases.changeAppRanFirstTime.changeAppRanFirstTime(false)
        isAppRanFirstTime =
            sharedPreferencesUseCases.checkIsAppRanFirstTimeUseCase.checkIsAppRanFirstTime()
    }

    fun createMeditationCourses() {
        viewModelScope.launch { meditationCoursesUseCases.createMeditationCoursesUseCase.invoke() }
    }

}