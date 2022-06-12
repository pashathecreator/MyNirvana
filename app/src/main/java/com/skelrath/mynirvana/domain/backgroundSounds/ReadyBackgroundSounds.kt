package com.skelrath.mynirvana.domain.backgroundSounds

import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound

enum class ReadyBackgroundSounds(val backgroundSound: BackgroundSound) {
    BackGroundSound0(
        BackgroundSound(name = "Без звука", icon = R.drawable.ic_without_sound_icon, sound = 0)
    ),

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