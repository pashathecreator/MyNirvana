package com.example.mynirvana.domain.meditations.readyMeditationsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditations.model.Meditation

enum class ReadyMeditations(val meditationButton: Meditation) {
    ReadyMeditation1(
        Meditation(
            header = "Спокойствие",
            time = 420,
            imageResourceId = R.drawable.guitar,
            soundResourceId = R.raw.fire_sound
        )
    ),
    ReadyMeditation2(
        Meditation(
            header = "Полная жизнь",
            time = 480,
            imageResourceId = R.drawable.ready_meditation2,
            soundResourceId = R.raw.guitar_sound

        )
    ),
    ReadyMeditation3(
        Meditation(
            header = "Концентрация",
            time = 600,
            imageResourceId = R.drawable.ready_meditation3,
            soundResourceId = R.raw.drums_sound
        )
    )

}