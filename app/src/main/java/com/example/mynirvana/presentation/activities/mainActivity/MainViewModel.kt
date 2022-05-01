package com.example.mynirvana.presentation.activities.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.example.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferencesUseCases: SharedPreferencesUseCases,
    private val meditationCoursesUseCases: MeditationCoursesUseCases
) :
    ViewModel() {
    var isAppRanFirstTime =
        sharedPreferencesUseCases.checkIsAppRanFirstTimeUseCase.checkIsAppRanFirstTime()

    fun setFalseToAppRanFirstTime() {
        sharedPreferencesUseCases.changeAppRanFirstTime.changeAppRanFirstTime(false)
        isAppRanFirstTime =
            sharedPreferencesUseCases.checkIsAppRanFirstTimeUseCase.checkIsAppRanFirstTime()
    }

    fun createMeditationCourses() {
        viewModelScope.launch { meditationCoursesUseCases.createMeditationCoursesUseCase.invoke() }
    }
}