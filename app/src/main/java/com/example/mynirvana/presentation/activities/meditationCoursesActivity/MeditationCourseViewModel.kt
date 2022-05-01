package com.example.mynirvana.presentation.activities.meditationCoursesActivity

import androidx.lifecycle.ViewModel
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeditationCourseViewModel @Inject constructor(private val meditationCoursesUseCases: MeditationCoursesUseCases) :
    ViewModel() {

    fun replaceMeditationCourse(meditationCourse: MeditationCourse) {

    }
}