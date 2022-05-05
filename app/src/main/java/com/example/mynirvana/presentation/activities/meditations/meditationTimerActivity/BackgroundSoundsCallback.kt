package com.example.mynirvana.presentation.activities.meditations.meditationTimerActivity

import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound

interface BackgroundSoundsCallback {
    fun sendPickedBackgroundSound(backgroundSound: BackgroundSound)
}