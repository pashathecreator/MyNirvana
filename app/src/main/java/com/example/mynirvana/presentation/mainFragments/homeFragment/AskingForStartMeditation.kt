package com.example.mynirvana.presentation.mainFragments.homeFragment

import com.example.mynirvana.domain.meditations.model.meditation.Meditation

interface AskingForStartMeditation {
    fun asksForStartMeditation(meditation: Meditation)
    fun onMeditationActivityDestroyed()
}