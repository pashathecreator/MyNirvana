package com.skelrath.mynirvana.domain.endSounds

import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.endSounds.model.EndSound

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
            icon = R.drawable.ic_meditation_bowl,
            sound = R.raw.meditation_bowl_sound,
            name = "Чаша"
        )
    )

}