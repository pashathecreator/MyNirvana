package com.example.mynirvana.presentation.activities.meditationTimerActivity

interface MeditationOnFinishFragmentCallback {
    fun meditationOnFinishUserChoice(userChoice: Boolean)
    fun meditationOnFinishFragmentDestroyed()
}