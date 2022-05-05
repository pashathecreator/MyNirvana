package com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity

interface MeditationCourseActivityCallback {
    fun meditationOnFinish(
        isMeditationCompleted: Boolean,
        isNecessaryToReturnToMeditationFragment: Boolean
    )
}