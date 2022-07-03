package com.skelrath.mynirvana.domain.backgroundSounds

import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound

enum class ReadyBackgroundSounds(val backgroundSound: BackgroundSound) {
    BackGroundSound0(
        BackgroundSound(
            name = "Без звука",
            icon = R.drawable.ic_without_sound_icon,
            sound = 0,
            colorForProgressIndicator = R.color.purple_brighter
        )
    ),
    BackGroundSound4(
        BackgroundSound(
            name = "Лес",
            icon = R.drawable.ic_noun_forest,
            backgroundImage = R.drawable.forest_background,
            sound = R.raw.forest_sound,
            colorForProgressIndicator = R.color.dark_green,
            buttonBackground = R.drawable.button_background_green
        )
    ),
    BackGroundSound1(
        BackgroundSound(
            name = "Огонь",
            icon = R.drawable.ic_noun_fire,
            backgroundImage = R.drawable.fire_for_meditation_background,
            sound = R.raw.fire_sound,
            colorForProgressIndicator = R.color.red,
            buttonBackground = R.drawable.button_background_red
        )
    ),
    BackGroundSound2(
        BackgroundSound(
            name = "Дождь",
            icon = R.drawable.ic_noun_rain,
            backgroundImage = R.drawable.rain_background_for_meditation,
            sound = R.raw.rain_sound,
            colorForProgressIndicator = R.color.rain,
            buttonBackground = R.drawable.button_background_rain
        )
    ),
    BackGroundSound3(
        BackgroundSound(
            name = "Океан",
            icon = R.drawable.ic_noun_ocean,
            backgroundImage = R.drawable.ocean_background,
            sound = R.raw.ocean_sound,
            colorForProgressIndicator = R.color.ocean,
            buttonBackground = R.drawable.button_background_ocean
        )
    )
}