package com.example.mynirvana.domain.backgroundSounds

import com.example.mynirvana.R
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound

enum class ReadyBackgroundSounds(val backgroundSound: BackgroundSound) {
    BackGroundSound1(
        BackgroundSound(
            name = "Огонь",
            icon = R.drawable.ic_noun_fire,
            sound = R.raw.fire_sound
        )
    ),
    BackGroundSound2(
        BackgroundSound(
            name = "Дождь",
            icon = R.drawable.ic_noun_rain,
            sound = R.raw.rain_sound
        )
    ),
    BackGroundSound3(
        BackgroundSound(
            name = "Океан",
            icon = R.drawable.ic_noun_ocean,
            sound = R.raw.ocean_sound
        )
    )

}