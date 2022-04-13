package com.example.mynirvana.presentation.meditationTimerActivity

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.meditationMusic.MusicPlayer
import com.example.mynirvana.domain.timer.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeditationTimerViewModel @Inject constructor(
    private val timer: Timer,
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    private var _remainingTime: MutableLiveData<Long> = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    init {
        viewModelScope.launch {
            timer.secondsRemaining.collect {
                _remainingTime.postValue(it)
            }
        }

    }


    fun startTimer(totalTimeInSeconds: Long) {
        timer.startTimer(totalTimeInSeconds)
    }

    fun pauseTimer() {
        timer.pauseTimer()
    }
}