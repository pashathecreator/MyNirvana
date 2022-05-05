package com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity

interface MeditationOnFinishFragmentCallback {
    fun meditationOnFinishUserChoice(userChoice: Boolean)
    fun meditationOnFinishFragmentDestroyed()
}