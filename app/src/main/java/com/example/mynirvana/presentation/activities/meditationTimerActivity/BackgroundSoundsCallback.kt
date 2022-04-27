package com.example.mynirvana.presentation.activities.meditationTimerActivity

import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound

interface BackgroundSoundsCallback {
    fun sendPickedBackgroundSound(backgroundSound: BackgroundSound)
}