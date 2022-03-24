package com.example.mynirvana.domain.endSounds

import com.example.mynirvana.R
import com.example.mynirvana.domain.endSounds.model.EndSound

enum class ReadyEndSounds(val endSound: EndSound) {

    ReadyEndSound1(
        EndSound(
            icon = R.drawable.ic_noun_saxophone,
            sound = R.raw.saxophone_sound,
            name = "Саксафон"
        )
    ),
    ReadyEndSound2(
        EndSound(
            icon = R.drawable.ic_noun_guitar,
            sound = R.raw.guitar_sound,
            name = "Гитара"
        )
    ),
    ReadyEndSound3(
        EndSound(
            icon = R.drawable.ic_noun_drum,
            sound = R.raw.drums_sound,
            name = "Барабаны"
        )
    )

}