package com.example.mynirvana.presentation.mainFragments.productivityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynirvana.domain.habit.model.Habit
import com.example.mynirvana.domain.habit.useCases.HabitUseCases
import com.example.mynirvana.domain.pomodoro.model.Pomodoro
import com.example.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.example.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.example.mynirvana.domain.task.model.Task
import com.example.mynirvana.domain.task.useCases.TaskUseCases
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class ProductivityViewModel @Inject constructor(
    private val pomodoroUseCases: PomodoroUseCases,
    private val taskUseCases: TaskUseCases,
    private val habitUseCases: HabitUseCases
) :
    ViewModel() {

    private val pomodorosMutableLiveData = MutableLiveData<List<Pomodoro>>()
    val pomodorosLiveData: LiveData<List<Pomodoro>>
        get() = pomodorosMutableLiveData

    private val today = Date(Calendar.getInstance().time.time)

    private val tasksMutableLiveData = MutableLiveData<List<Task>>()
    val tasksLiveData: LiveData<List<Task>>
        get() = tasksMutableLiveData

    private val habitsMutableLiveData = MutableLiveData<List<Habit>>()
    val habitsLiveData: LiveData<List<Habit>>
        get() = habitsMutableLiveData

    private fun getUserPomodorosFromDatabase() {
        viewModelScope.launch {
            pomodoroUseCases.getPomodorosUseCase.invoke().collect {
                pomodorosMutableLiveData.postValue(it)
            }
        }
    }

    init {
        getUserPomodorosFromDatabase()
        getHabitsFromDatabase()
        getTasksOnCurrentDate(today)
    }

    fun getTasksOnCurrentDate(date: Date) {
        viewModelScope.launch {
            taskUseCases.getTasksUseCase.invoke().collect {
                val taskByDate = mutableListOf<Task>()

                for (task in it) {
                    if (TimeWorker.compareTwoDates(task.dateOfTask, date)) {
                        taskByDate.add(task)
                    }
                }

                tasksMutableLiveData.postValue(taskByDate)
            }
        }

    }

    private fun getHabitsFromDatabase() {
        viewModelScope.launch {
            habitUseCases.getHabitsUseCase.invoke().collect {
                habitsMutableLiveData.postValue(it)
            }
        }
    }

    fun deleteHabit(position: Int, functionToInvokeAfterDeleting: () -> Unit) {
        viewModelScope.launch {
            habitsLiveData.value?.get(position)?.let { habitUseCases.deleteHabitUseCase.invoke(it) }
        }.invokeOnCompletion {
            functionToInvokeAfterDeleting()
        }
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

    fun deletePomodoro(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.deletePomodoroUseCase.invoke(pomodoro)
        }
    }
}