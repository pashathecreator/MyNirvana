package com.skelrath.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import androidx.lifecycle.ViewModel
import com.skelrath.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound

class BackgroundSoundChoiceViewModel : ViewModel() {

    fun getBackgroundSounds(): List<BackgroundSound> {
        val readyMeditations = mutableListOf<BackgroundSound>()

        ReadyBackgroundSounds.values().forEach {
            val name = it.backgroundSound.name
            val image = it.backgroundSound.icon
            val sound = it.backgroundSound.sound
            val backgroundImage = it.backgroundSound.backgroundImage
            val colorForProgressIndicator = it.backgroundSound.colorForProgressIndicator
            val buttonBackground = it.backgroundSound.buttonBackground


            readyMeditations.add(
                BackgroundSound(
                    name = name,
                    icon = image,
                    sound = sound,
                    backgroundImage = backgroundImage,
                    colorForProgressIndicator = colorForProgressIndicator,
                    buttonBackground = buttonBackground
                )
            )
        }

        return readyMeditations

    }


}