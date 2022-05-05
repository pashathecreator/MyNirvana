package com.example.mynirvana.presentation.mainFragments.productivityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.example.mynirvana.domain.pomodoro.useCases.GetPomodorosUseCase
import com.example.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductivityViewModel @Inject constructor(private val pomodorosUseCases: PomodoroUseCases) :
    ViewModel() {

    private val pomodorosMutableLiveData = MutableLiveData<List<Pomodoro>>()
    val pomodorosLiveData: LiveData<List<Pomodoro>>
        get() = pomodorosMutableLiveData

    private fun getUserPomodorosFromDatabase() {
        viewModelScope.launch {
            pomodorosUseCases.getPomodorosUseCase.invoke().collect {
                pomodorosMutableLiveData.postValue(it)
            }
        }
    }

    init {
        getUserPomodorosFromDatabase()
    }

    fun getReadyPomodoros(): List<Pomodoro> {
        val readyPomodoros = mutableListOf<Pomodoro>()

        ReadyPomodoros.values().forEach {
            readyPomodoros.add(
                Pomodoro(
                    name = it.pomodoro.name,
                    workTimeInSeconds = it.pomodoro.workTimeInSeconds,
                    relaxTimeInSeconds = it.pomodoro.relaxTimeInSeconds,
                    quantityOfCircles = it.pomodoro.quantityOfCircles,
                    imageResourceId = it.pomodoro.imageResourceId
                )
            )
        }

        return readyPomodoros
    }
}