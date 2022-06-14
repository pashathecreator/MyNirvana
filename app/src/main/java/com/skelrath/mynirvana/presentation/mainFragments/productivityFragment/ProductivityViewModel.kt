package com.skelrath.mynirvana.presentation.mainFragments.productivityFragment

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.useCases.HabitUseCases
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.readyPomodorosData.ReadyPomodoros
import com.skelrath.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.domain.task.useCases.TaskUseCases
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import com.skelrath.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*
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

    private val habitsMutableLiveData = habitUseCases.getHabitsUseCase.invoke().asLiveData()
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
        getTasksOnCurrentDate(today)
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

    fun getTasksOnCurrentDate(date: Date) {
        viewModelScope.launch {
            taskUseCases.getTasksUseCase.invoke().collect {
                val taskByDate = mutableListOf<Task>()

                for (task in it) {
                    if (TimeWorker.compareTwoDates(task.dateOfTask!!, date)) {
                        taskByDate.add(task)
                    }
                }

                tasksMutableLiveData.postValue(taskByDate)
            }
        }

    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitUseCases.deleteHabitUseCase.invoke(habit)
            deleteHabitFromRealTimeDatabase(habit)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.deleteTaskUseCase.invoke(task)
            deleteTaskFromRealTimeDatabase(task)
        }
    }


    fun deletePomodoro(pomodoro: Pomodoro) {
        viewModelScope.launch {
            pomodoroUseCases.deletePomodoroUseCase.invoke(pomodoro)
            deletePomodoroFromRealTimeDatabase(pomodoro)
        }
    }

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid

    private fun deleteHabitFromRealTimeDatabase(habit: Habit) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.HABITS).child(habit.fireBaseId.toString()).removeValue()
    }

    private fun deleteTaskFromRealTimeDatabase(task: Task) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.POMODOROS).child(task.fireBaseId.toString()).removeValue()
    }

    private fun deletePomodoroFromRealTimeDatabase(pomodoro: Pomodoro) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.POMODOROS).child(pomodoro.fireBaseId.toString()).removeValue()
    }
}
