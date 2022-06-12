package com.skelrath.mynirvana.presentation.recycler.onClickListeners.meditations

import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation

interface MeditationOnClickListener {
    fun onMeditationStart(meditation: Meditation)
    fun onMeditationDelete(meditation: Meditation)
}