package com.example.mynirvana.domain.meditationButtons.readyMeditationButtonsData

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditationButtons.model.MeditationButton

enum class ReadyMeditations(val meditationButton: MeditationButton) {
    ReadyMeditation1(
        MeditationButton(
            header = "Спокойствие",
            time = 7,
            imageResourceId = R.drawable.guitar,
        )
    ),
    ReadyMeditation2(
        MeditationButton(
            header = "Полная жизнь",
            time = 8,
            imageResourceId = R.drawable.ready_meditation2,

            )
    ),
    ReadyMeditation3(
        MeditationButton(
            header = "Концентрация",
            time = 10,
            imageResourceId = R.drawable.ready_meditation3,
        )
    )

}