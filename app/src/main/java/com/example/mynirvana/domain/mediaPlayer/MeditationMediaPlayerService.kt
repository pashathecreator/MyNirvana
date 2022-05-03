package com.example.mynirvana.domain.mediaPlayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MeditationMediaPlayerService(private val context: Context) : MeditationMediaPlayer() {
    private lateinit var backgroundSoundMediaPlayer: MediaPlayer
    private lateinit var endSoundMediaPlayer: MediaPlayer

    override fun startBackgroundSound(soundResourceId: Int) {
        backgroundSoundMediaPlayer = MediaPlayer.create(context, soundResourceId)
        backgroundSoundMediaPlayer.start()
        backgroundSoundMediaPlayer.isLooping = true
    }

    override fun startEndSound(soundResourceId: Int) {
        endSoundMediaPlayer = MediaPlayer.create(context, soundResourceId)
        endSoundMediaPlayer.start()
        endSoundMediaPlayer.isLooping = true
    }

    override fun pauseBackgroundSound() {
        backgroundSoundMediaPlayer.stop()
    }

    override fun stopMeditationMediaPlayer() {
        endSoundMediaPlayer.stop()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}