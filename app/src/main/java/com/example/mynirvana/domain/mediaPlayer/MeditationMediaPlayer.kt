package com.example.mynirvana.domain.mediaPlayer

import android.app.Service

abstract class MeditationMediaPlayer : Service() {
    abstract fun startBackgroundSound(soundResourceId: Int)
    abstract fun startEndSound(soundResourceId: Int)
    abstract fun pauseBackgroundSound()
    abstract fun stopMeditationMediaPlayer()
}