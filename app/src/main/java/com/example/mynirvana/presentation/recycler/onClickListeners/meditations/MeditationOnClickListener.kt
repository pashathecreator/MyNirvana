package com.example.mynirvana.presentation.recycler.onClickListeners.meditations

import com.example.mynirvana.domain.meditations.model.meditation.Meditation

interface MeditationOnClickListener {
    fun onMeditationStart(meditation: Meditation)
    fun onMeditationDelete(meditation: Meditation)
}