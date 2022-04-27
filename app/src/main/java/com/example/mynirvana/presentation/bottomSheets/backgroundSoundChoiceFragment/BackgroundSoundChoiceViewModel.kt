package com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import androidx.lifecycle.ViewModel
import com.example.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound

class BackgroundSoundChoiceViewModel : ViewModel() {

    fun getBackgroundSounds(): List<BackgroundSound> {
        val readyMeditations = mutableListOf<BackgroundSound>()

        ReadyBackgroundSounds.values().forEach {
            val name = it.backgroundSound.name
            val image = it.backgroundSound.icon
            val sound = it.backgroundSound.sound

            readyMeditations.add(BackgroundSound(name = name, icon = image, sound = sound))
        }

        return readyMeditations

    }


}