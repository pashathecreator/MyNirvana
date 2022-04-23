package com.example.mynirvana.presentation.meditationTimerActivity

interface MeditationOnFinishFragmentCallback {
    fun meditationOnFinishUserChoice(userChoice: Boolean)
    fun meditationOnFinishFragmentDestroyed()
}