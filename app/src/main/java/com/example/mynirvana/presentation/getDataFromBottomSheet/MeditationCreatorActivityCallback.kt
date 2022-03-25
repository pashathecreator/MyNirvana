package com.example.mynirvana.presentation.getDataFromBottomSheet

import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.domain.endSounds.model.EndSound

interface MeditationCreatorActivityCallback {
    fun sendPickedBackgroundSound(backgroundSound: BackgroundSound)

    fun sendPickedEndSound(endSound: EndSound)

    fun sendPickedTime(minutes: Int, seconds: Int)
}