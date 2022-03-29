package com.example.mynirvana.domain.meditationButtons.readyMeditationButtonsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditationButtons.model.Meditation

enum class ReadyMeditations(val meditationButton: Meditation) {
    ReadyMeditation1(
        Meditation(
            header = "Спокойствие",
            time = 7,
            imageResourceId = R.drawable.guitar,
        )
    ),
    ReadyMeditation2(
        Meditation(
            header = "Полная жизнь",
            time = 8,
            imageResourceId = R.drawable.ready_meditation2,

            )
    ),
    ReadyMeditation3(
        Meditation(
            header = "Концентрация",
            time = 10,
            imageResourceId = R.drawable.ready_meditation3,
        )
    )

}