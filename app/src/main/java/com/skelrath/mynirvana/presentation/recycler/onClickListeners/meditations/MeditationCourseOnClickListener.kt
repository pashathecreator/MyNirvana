package com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations

import com.skelrath.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse

interface MeditationCourseOnClickListener {
    fun onMeditationCourseStart(meditationCourse: MeditationCourse)

}