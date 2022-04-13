package com.example.mynirvana.domain.meditationMusic

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicPlayerService() : Service(), MusicPlayer {
    private lateinit var mediaPlayer: MediaPlayer

    override fun startMusic(soundResourceId: Int) {
        mediaPlayer = MediaPlayer.create(applicationContext, soundResourceId)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

    }

    override fun endMusic() {
        mediaPlayer.stop()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}