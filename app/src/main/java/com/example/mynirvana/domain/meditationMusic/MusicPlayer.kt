package com.example.mynirvana.domain.meditationMusic

interface MusicPlayer {
    fun startMusic(soundResourceId: Int)
    fun endMusic()
}