package com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PomodoroCreatorViewModel @Inject constructor(private val pomodoroUseCases: PomodoroUseCases) :
    ViewModel() {

    fun savePomodoroTimer(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.addPomodoroUseCase.invoke(pomodoro)
        }
    }
}