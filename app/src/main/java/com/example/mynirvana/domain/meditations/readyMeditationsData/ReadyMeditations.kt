package com.example.mynirvana.domain.meditations.readyMeditationsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditations.model.meditation.Meditation

enum class ReadyMeditations(val meditation: Meditation) {
    ReadyMeditation1(
        Meditation(
            name = "Спокойствие",
            time = 420,
            imageResourceId = R.drawable.ic_rectangle_green,
            backgroundSoundResourceId = R.raw.fire_sound,
            endSoundResourceId = R.raw.guitar_sound,
            isMeditationCanBeDeleted = false
        )
    ),
    ReadyMeditation2(
        Meditation(
            name = "Полная жизнь",
            time = 480,
            imageResourceId = R.drawable.ic_rectangle_dark_blue,
            backgroundSoundResourceId = R.raw.fire_sound,
            endSoundResourceId = R.raw.guitar_sound,
            isMeditationCanBeDeleted = false

        )
    ),
    ReadyMeditation3(
        Meditation(
            name = "Концентрация",
            time = 600,
            imageResourceId = R.drawable.ic_rectangle_blue,
            backgroundSoundResourceId = R.raw.rain_sound,
            endSoundResourceId = R.raw.drums_sound,
            isMeditationCanBeDeleted = false
        )
    )

}