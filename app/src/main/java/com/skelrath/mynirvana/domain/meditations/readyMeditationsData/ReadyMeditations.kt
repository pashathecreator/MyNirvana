package com.skelrath.mynirvana.domain.meditations.readyMeditationsData

import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation

enum class ReadyMeditations(val meditation: Meditation) {
    ReadyMeditation1(
        Meditation(
            name = "Спокойствие",
            time = 420,
            imageResourceId = R.drawable.ic_rectangle_green,
            backgroundSoundResourceId = R.raw.fire_sound,
            endSoundResourceId = R.raw.guitar_sound,
            isMeditationCanBeDeleted = false,
            isMeditationCompleted = false,
            isMeditationCanBeRestarted = true
        )
    ),
    ReadyMeditation2(
        Meditation(
            name = "Полная жизнь",
            time = 480,
            imageResourceId = R.drawable.ic_rectangle_dark_blue,
            backgroundSoundResourceId = R.raw.forest_sound,
            endSoundResourceId = R.raw.guitar_sound,
            isMeditationCanBeDeleted = false,
            isMeditationCompleted = false,
            isMeditationCanBeRestarted = true

        )
    ),
    ReadyMeditation3(
        Meditation(
            name = "Концентрация",
            time = 600,
            imageResourceId = R.drawable.ic_rectangle_blue,
            backgroundSoundResourceId = R.raw.rain_sound,
            endSoundResourceId = R.raw.meditation_bowl_sound,
            isMeditationCanBeDeleted = false,
            isMeditationCompleted = false,
            isMeditationCanBeRestarted = true
        )
    )

}