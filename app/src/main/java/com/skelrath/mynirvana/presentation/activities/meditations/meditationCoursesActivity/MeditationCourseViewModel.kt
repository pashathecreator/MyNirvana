package com.skelrath.mynirvana.presentation.activities.meditations.meditationCoursesActivity

import androidx.lifecycle.*
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationCourseViewModel @Inject constructor(private val meditationCoursesUseCases: MeditationCoursesUseCases) :
    ViewModel() {

    fun insertMeditationListInMeditationCourse(meditationList: List<Meditation>, id: Int) {
        viewModelScope.launch {
            meditationCoursesUseCases.insertMeditationListUseCase.invoke(
                meditationList,
                id
            )
        }
    }

    fun resetMeditationListInMeditationCourse(meditationList: List<Meditation>, id: Int) {
        for (meditation in meditationList) {
            meditation.isMeditationCompleted = false
        }
        viewModelScope.launch {
            meditationCoursesUseCases.insertMeditationListUseCase(meditationList, id)
        }
    }

}