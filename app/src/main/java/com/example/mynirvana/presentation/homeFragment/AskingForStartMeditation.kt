package com.example.mynirvana.presentation.homeFragment

import com.example.mynirvana.domain.meditations.model.Meditation

interface AskingForStartMeditation {
    fun asksForStartMeditation(meditation: Meditation)
    fun onMeditationCreatorActivityDestroyed()
}