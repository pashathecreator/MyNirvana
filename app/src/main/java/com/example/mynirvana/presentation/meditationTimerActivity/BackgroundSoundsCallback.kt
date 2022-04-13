package com.example.mynirvana.presentation.meditationTimerActivity

import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound

interface BackgroundSoundsCallback {
    fun sendPickedBackgroundSound(backgroundSound: BackgroundSound)
}