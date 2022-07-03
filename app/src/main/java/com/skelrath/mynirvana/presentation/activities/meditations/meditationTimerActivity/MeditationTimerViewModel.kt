package com.skelrath.mynirvana.presentation.activities.meditations.meditationTimerActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skelrath.mynirvana.domain.backgroundSounds.ReadyBackgroundSounds
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.skelrath.mynirvana.domain.mediaPlayer.MusicPlayer
import com.skelrath.mynirvana.domain.timer.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationTimerViewModel @Inject constructor(
    private val timer: Timer, private val musicPlayer: MusicPlayer
) : ViewModel() {

    private var _remainingTime: MutableLiveData<Long> = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private var currentBackgroundSound: Int = 0
    private var currentEndSound: Int = 0

    fun providesBackgroundSound(currentBackgroundSound: Int) {
        this.currentBackgroundSound = currentBackgroundSound
    }

    fun providesEndSound(currentEndSound: Int) {
        this.currentEndSound = currentEndSound
    }

    init {
        viewModelScope.launch {
            timer.secondsRemaining.collect {
                _remainingTime.postValue(it)
                if (it == 0L) {
                    pauseBackgroundSound()
                    startEndSound(currentEndSound)
                }
            }
        }
    }

    fun startTimer(totalTimeInSeconds: Long) {
        timer.startTimer(totalTimeInSeconds)
    }

    fun pauseTimer() {
        timer.pauseTimer()
    }

    fun startBackgroundSound(soundResourceId: Int) {
        if (soundResourceId == 0) {
            pauseBackgroundSound()
        } else {
            musicPlayer.startBackgroundSound(soundResourceId)
        }
    }

    private fun startEndSound(soundResourceId: Int) {
        musicPlayer.startEndSound(soundResourceId)
    }

    fun pauseBackgroundSound() {
        musicPlayer.stopSound()
    }

    fun stopMeditationMediaPlayer() {
        musicPlayer.stopSound()
    }

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