package com.example.mynirvana.presentation.recycler.onClickListeners

import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse

interface MeditationCourseOnClickListener {
    fun onMeditationCourseStart(meditationCourse: MeditationCourse)
}