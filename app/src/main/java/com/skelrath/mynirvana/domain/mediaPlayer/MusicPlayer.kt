package com.skelrath.mynirvana.domain.mediaPlayer

import android.app.Service

abstract class MusicPlayer : Service() {
    abstract fun startSound(soundResourceId: Int)
    abstract fun startBackgroundSound(soundResourceId: Int)
    abstract fun startEndSound(soundResourceId: Int)
    abstract fun stopSound()
}