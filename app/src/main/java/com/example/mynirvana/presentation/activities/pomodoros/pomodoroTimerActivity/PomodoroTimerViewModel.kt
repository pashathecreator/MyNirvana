package com.example.mynirvana.presentation.activities.pomodoros.pomodoroTimerActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.timer.Timer
import com.example.mynirvana.presentation.activities.pomodoros.PomodoroTimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PomodoroTimerViewModel @Inject constructor(private val timer: Timer) : ViewModel() {

    private var _remainingTime: MutableLiveData<Long> = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private var _pomodoroTimerState: MutableLiveData<PomodoroTimerState> =
        MutableLiveData<PomodoroTimerState>()
    val pomodoroTimerState: LiveData<PomodoroTimerState>
        get() = _pomodoroTimerState


    init {
        viewModelScope.launch {
            _pomodoroTimerState.postValue(PomodoroTimerState.Work)

            timer.secondsRemaining.collect {
                _remainingTime.postValue(it)
                if (it == 0L) {
                    changePomodoroTimerState()
                }
            }
        }
    }

    private fun changePomodoroTimerState() {
        when (pomodoroTimerState.value) {
            PomodoroTimerState.Work -> {
                _pomodoroTimerState.postValue(PomodoroTimerState.Relax)
            }

            PomodoroTimerState.Relax -> {
                _pomodoroTimerState.postValue(PomodoroTimerState.Work)
            }
        }
    }

    fun startTimer(totalTimeInSeconds: Long) {
        timer.startTimer(totalTimeInSeconds)
    }

    fun stopTimer() {
        timer.pauseTimer()
    }

    fun skipCurrentState(nextTotalTimeInSeconds: Long) {
        stopTimer()
        changePomodoroTimerState()
        startTimer(nextTotalTimeInSeconds)
    }


}