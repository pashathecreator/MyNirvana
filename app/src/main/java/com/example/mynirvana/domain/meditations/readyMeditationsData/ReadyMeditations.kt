package com.example.mynirvana.domain.meditations.readyMeditationsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditations.model.Meditation

enum class ReadyMeditations(val meditationButton: Meditation) {
    ReadyMeditation1(
        Meditation(
            header = "Спокойствие",
            time = 420,
            imageResourceId = R.drawable.ic_rectangle_green,
            soundResourceId = R.raw.fire_sound,
            isMeditationCanBeDeleted = false
        )
    ),
    ReadyMeditation2(
        Meditation(
            header = "Полная жизнь",
            time = 480,
            imageResourceId = R.drawable.ic_rectangle_dark_blue,
            soundResourceId = R.raw.fire_sound,
            isMeditationCanBeDeleted = false

        )
    ),
    ReadyMeditation3(
        Meditation(
            header = "Концентрация",
            time = 600,
            imageResourceId = R.drawable.ic_rectangle_blue,
            soundResourceId = R.raw.rain_sound,
            isMeditationCanBeDeleted = false
        )
    )

}