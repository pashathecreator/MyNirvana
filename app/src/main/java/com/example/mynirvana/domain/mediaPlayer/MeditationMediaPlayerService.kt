package com.example.mynirvana.domain.mediaPlayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MeditationMediaPlayerService(private val context: Context) : MeditationMediaPlayer() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun startBackgroundSound(soundResourceId: Int) {
        mediaPlayer = MediaPlayer.create(context, soundResourceId)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    override fun startEndSound(soundResourceId: Int) {
        mediaPlayer = MediaPlayer.create(context, soundResourceId)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    override fun stopSound() {
        mediaPlayer.stop()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}