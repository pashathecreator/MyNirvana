package com.skelrath.mynirvana.presentation.activities.signUpActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.skelrath.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sharedPreferencesUseCases: SharedPreferencesUseCases,
    private val meditationCoursesUseCases: MeditationCoursesUseCases
) : ViewModel() {

    fun setUserName(name: String) {
        viewModelScope.launch {
            sharedPreferencesUseCases.changeUserNameUseCase.invoke(name)
        }
    }

    fun createMeditationCourses() {
        viewModelScope.launch { meditationCoursesUseCases.createMeditationCoursesUseCase.invoke() }
    }

}