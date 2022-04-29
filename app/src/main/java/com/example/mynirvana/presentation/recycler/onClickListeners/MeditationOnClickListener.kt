package com.example.mynirvana.presentation.recycler.onClickListeners

import com.example.mynirvana.domain.meditations.model.Meditation

interface MeditationOnClickListener {
    fun onMeditationStart(meditation: Meditation)
    fun onMeditationSureDelete(meditation: Meditation)
}