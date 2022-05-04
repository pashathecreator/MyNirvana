package com.example.mynirvana.presentation.activities.meditationCoursesActivity

interface MeditationCourseActivityCallback {
    fun meditationOnFinish(
        isMeditationCompleted: Boolean,
        isNecessaryToReturnToMeditationFragment: Boolean
    )
}